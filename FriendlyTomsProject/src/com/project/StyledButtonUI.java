package com.project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * Based upon code sourced from:
 * https://stackoverflow.com/questions/23698092/design-button-in-java-like-in-css
 */

class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent mainComponent) {
        super.installUI(mainComponent);
        AbstractButton button = (AbstractButton) mainComponent;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics mainGraphic, JComponent mainComponent) {
        AbstractButton b = (AbstractButton) mainComponent;
        paintBackground(mainGraphic, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(mainGraphic, mainComponent);
    }

    private void paintBackground (Graphics mainGraphic, JComponent mainComponent, int yOffset) {
        Dimension size =mainComponent.getSize();
        Graphics2D g2 = (Graphics2D) mainGraphic;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mainGraphic.setColor(mainComponent.getBackground().darker());
        mainGraphic.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        mainGraphic.setColor(mainComponent.getBackground());
        mainGraphic.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}