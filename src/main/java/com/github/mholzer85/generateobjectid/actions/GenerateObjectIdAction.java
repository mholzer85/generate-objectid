package com.github.mholzer85.generateobjectid.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class GenerateObjectIdAction extends AnAction {
	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		int cursorOffset = caretModel.getOffset();

		CharSequence output = "" + new ObjectId();

		WriteCommandAction.writeCommandAction(e.getProject()).run(() -> {
			if (caretModel.getCurrentCaret().hasSelection()) {
				int selectionStart = caretModel.getCurrentCaret().getSelectionStart();
				int selectionEnd = caretModel.getCurrentCaret().getSelectionEnd();
				editor.getDocument().replaceString(selectionStart, selectionEnd, output);
			}
			else {
				editor.getDocument().insertString(cursorOffset, output);
			}
		});
	}



	@Override
	public void update(AnActionEvent e) {
		Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		e.getPresentation().setEnabledAndVisible(caretModel.getCaretCount() > 0);
	}
}
