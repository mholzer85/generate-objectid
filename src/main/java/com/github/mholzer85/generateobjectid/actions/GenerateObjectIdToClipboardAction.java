package com.github.mholzer85.generateobjectid.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GenerateObjectIdToClipboardAction extends AnAction {
	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		getSystemClipboard().setContents(new StringSelection(new ObjectId().toString()), null);
	}



	@Override
	public void update(AnActionEvent e) {
		e.getPresentation().setEnabledAndVisible(true);
	}



	public static Clipboard getSystemClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
}
