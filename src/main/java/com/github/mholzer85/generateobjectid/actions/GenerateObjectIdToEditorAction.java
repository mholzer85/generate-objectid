package com.github.mholzer85.generateobjectid.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class GenerateObjectIdToEditorAction extends AnAction {
	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		int cursorOffset = caretModel.getOffset();

		CharSequence output = "" + new ObjectId();

		PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
		if (psiFile != null) {
			PsiElement psiElement = psiFile.findElementAt(cursorOffset);
			if (psiElement != null && !"STRING_LITERAL".equals(psiElement.getNode().getElementType().toString())) {
				output = "\"" + output + "\"";
			}
		}

		final CharSequence finalOutput = output;
		WriteCommandAction.writeCommandAction(e.getProject()).run(() -> {
			if (caretModel.getCurrentCaret().hasSelection()) {
				int selectionStart = caretModel.getCurrentCaret().getSelectionStart();
				int selectionEnd = caretModel.getCurrentCaret().getSelectionEnd();
				editor.getDocument().replaceString(selectionStart, selectionEnd, finalOutput);
			}
			else {
				editor.getDocument().insertString(cursorOffset, finalOutput);
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
