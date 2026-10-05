/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.font.TextRenderer
 *  net.minecraft.client.gui.DrawContext
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.widget.EntryListWidget$Entry
 *  net.minecraft.client.gui.Element
 *  net.minecraft.client.gui.widget.ElementListWidget
 *  net.minecraft.client.gui.widget.ElementListWidget$Entry
 *  net.minecraft.text.StringVisitable
 *  net.minecraft.client.gui.Selectable
 *  net.minecraft.client.gui.widget.TextWidget
 *  net.minecraft.client.gui.tooltip.Tooltip
 */
package com.nonid.integration;

import com.nonid.NeedsOfNature;
import com.nonid.integration.NonModMenuScreens;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.tooltip.Tooltip;

final class SettingsList
extends ElementListWidget<SettingsList.RowEntry> {
    private static boolean debugLogged;
    private static int debugRenderLog;
    private static final boolean DEBUG_LAYOUT =
            Boolean.parseBoolean(System.getProperty("non.debug.ui", "false"));

    /**
     * @param width  界面宽度
     * @param height 界面/列表完整高度（用于滚动计算，通常传 Screen.height）
     * @param top    可见区域上边界
     * @param bottom 可见区域下边界（不含）
     */
    SettingsList(MinecraftClient client, int width, int height, int top, int bottom) {
        // 1.20.1: (client, width, height, top, bottom, itemHeight)
        super(client, width, height, top, bottom, 32);
        this.centerListVertically = false;
        this.setRenderHeader(false, 0);
        if (DEBUG_LAYOUT && !debugLogged) {
            debugLogged = true;
            NeedsOfNature.LOGGER.info(
                    "[UI] SettingsList ctor itemHeight={} top={} bottom={} left={} right={} w={} h={} rowW={} rowL={}",
                    this.itemHeight, this.top, this.bottom, this.left, this.right,
                    this.width, this.height, this.getRowWidth(), this.getRowLeft());
        }
    }

    void addEntryRow(RowEntry entry) {
        super.addEntry(entry);
        entry.park();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 兜底：任何仍停在 (0,0) 的列表控件都先移出屏幕，避免堆叠
        try {
            for (RowEntry entry : this.children()) {
                if (entry != null) {
                    entry.parkIfOrigin();
                }
            }
        } catch (Throwable ignored) {
        }
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * 把条目绘制裁剪在列表可视区内，避免控件画到标题/底部按钮上。
     */
    @Override
    protected void renderEntry(DrawContext context, int mouseX, int mouseY, float delta, int index, int x, int y, int width, int height) {
        if (DEBUG_LAYOUT) {
            if (debugRenderLog++ < 30) {
                NeedsOfNature.LOGGER.info("[UI] renderEntry idx={} x={} y={} w={} h={}", index, x, y, width, height);
            }
            context.fill(x, y, x + width, y + height, 0x22FF0000);
            context.drawText(this.client.textRenderer, "#" + index + " y=" + y, x + 2, y + 2, 0xFFFF00, true);
        }
        try {
            context.enableScissor(this.left, this.top, this.right, this.bottom);
            super.renderEntry(context, mouseX, mouseY, delta, index, x, y, width, height);
            context.disableScissor();
        } catch (Throwable t) {
            try {
                super.renderEntry(context, mouseX, mouseY, delta, index, x, y, width, height);
            } catch (Throwable ignored) {
            }
            try {
                context.disableScissor();
            } catch (Throwable ignored) {
            }
        }
    }

    public int getRowWidth() {
        // 不要宽过界面，否则控件会压到滚动条/屏幕外
        return Math.min(400, Math.max(280, this.width - 48));
    }

    public int getRowLeft() {
        return (this.width - this.getRowWidth()) / 2;
    }

    @Override
    protected int getScrollbarPositionX() {
        // 默认在 width/2+124，会盖住宽行控件；放到行右侧外
        return this.getRowLeft() + this.getRowWidth() + 6;
    }

    static final class RowEntry
    extends ElementListWidget.Entry<RowEntry> {
        private final TextRenderer textRenderer;
        private final Text label;
        private final TextWidget labelWidget;
        private final ClickableWidget primary;
        private final ClickableWidget reset;
        private final RowType type;
        private final List<ClickableWidget> widgets;

        private RowEntry(TextRenderer textRenderer, Text label, ClickableWidget primary, ClickableWidget reset, RowType type, Tooltip tooltip) {
            this.textRenderer = textRenderer;
            this.label = label;
            this.labelWidget = label == null ? null : new TextWidget(0, 0, 0, 20, label, textRenderer).alignLeft();
            this.primary = primary;
            this.reset = reset;
            this.type = type;
            this.widgets = new ArrayList<ClickableWidget>(2);
            if (primary != null) {
                this.widgets.add(primary);
            }
            if (reset != null) {
                this.widgets.add(reset);
            }
            if (tooltip != null) {
                if (this.labelWidget != null) {
                    NonModMenuScreens.setTooltip((ClickableWidget)this.labelWidget, tooltip);
                }
                if (this.primary != null) {
                    NonModMenuScreens.setTooltip(this.primary, tooltip);
                }
            }
            // 未参与布局前先移出屏幕，避免 (0,0) 堆叠绘制
            this.park();
        }

        private void park() {
            if (this.labelWidget != null) {
                this.labelWidget.setY(-10000);
            }
            for (ClickableWidget w : this.widgets) {
                w.setY(-10000);
            }
        }

        private void parkIfOrigin() {
            if (this.labelWidget != null && this.labelWidget.getY() == 0) {
                this.labelWidget.setY(-10000);
            }
            for (ClickableWidget w : this.widgets) {
                if (w.getY() == 0) {
                    w.setY(-10000);
                }
            }
        }

        static RowEntry labeledField(TextRenderer textRenderer, Text label, ClickableWidget field, ClickableWidget reset, Tooltip tooltip) {
            return new RowEntry(textRenderer, label, field, reset, RowType.LABELED_FIELD, tooltip);
        }

        static RowEntry groupedField(TextRenderer textRenderer, Text label, Tooltip tooltip, ClickableWidget ... fields) {
            RowEntry entry = new RowEntry(textRenderer, label, null, null, RowType.GROUPED_FIELD, tooltip);
            if (fields != null) {
                for (ClickableWidget field : fields) {
                    if (field == null) continue;
                    entry.widgets.add(field);
                }
            }
            // 构造里的 park() 发生在 add 之前，这里必须再 park 一次
            entry.park();
            return entry;
        }

        static RowEntry buttonWithReset(TextRenderer textRenderer, ClickableWidget button, Tooltip tooltip) {
            return new RowEntry(textRenderer, null, button, null, RowType.BUTTON_RESET, tooltip);
        }

        static RowEntry sectionHeader(TextRenderer textRenderer, Text label) {
            return new RowEntry(textRenderer, label, null, null, RowType.SECTION_HEADER, null);
        }

        public void render(DrawContext context, int index, int rowY, int rowX, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean hovered, float delta) {
            // 行完全在可视区外时不布局，防止控件残影
            if (rowY + rowHeight < 0 || rowY > 4000) {
                this.park();
                return;
            }
            int widgetY = rowY + Math.max(0, (rowHeight - 20) / 2);
            if (this.type == RowType.SECTION_HEADER) {
                if (this.label != null) {
                    int bandY = rowY + 3;
                    int bandH = 18;
                    int centerX = rowX + rowWidth / 2;
                    int textY = rowY + 8;
                    int lineY = rowY + 12;
                    int textW = this.textRenderer.getWidth((StringVisitable)this.label);
                    int leftEnd = centerX - textW / 2 - 10;
                    int rightStart = centerX + textW / 2 + 10;
                    context.fill(rowX + 2, bandY, rowX + rowWidth - 2, bandY + bandH, 0x33000000);
                    context.fill(rowX + 8, lineY, Math.max(rowX + 8, leftEnd), lineY + 1, 0x66666666);
                    context.fill(Math.min(rowX + rowWidth - 8, rightStart), lineY, rowX + rowWidth - 8, lineY + 1, 0x66666666);
                    context.drawCenteredTextWithShadow(this.textRenderer, this.label, centerX, textY, -3158065);
                }
                return;
            }
            if (this.type == RowType.GROUPED_FIELD) {
                int totalW = 0;
                for (ClickableWidget widget : this.widgets) {
                    totalW += widget.getWidth();
                }
                int x = rowX + rowWidth - (totalW += Math.max(0, this.widgets.size() - 1) * 4);
                if (this.labelWidget != null) {
                    int labelWidth = Math.max(20, x - rowX - 8);
                    this.labelWidget.setX(rowX + 4);
                    this.labelWidget.setY(rowY + 4);
                    this.labelWidget.setWidth(labelWidth);
                    this.labelWidget.alignLeft();
                }
                for (ClickableWidget widget : this.widgets) {
                    widget.setX(x);
                    widget.setY(widgetY);
                    x += widget.getWidth() + 4;
                }
            } else if (this.primary != null) {
                if (this.type == RowType.LABELED_FIELD) {
                    int resetW = this.reset == null ? 0 : this.reset.getWidth();
                    int fieldW = this.primary.getWidth();
                    int resetX = rowX + rowWidth - resetW;
                    int fieldX = resetX - 6 - fieldW;
                    this.primary.setX(fieldX);
                    this.primary.setY(widgetY);
                    if (this.reset != null) {
                        this.reset.setX(resetX);
                        this.reset.setY(widgetY);
                    }
                    if (this.labelWidget != null) {
                        int labelWidth = Math.max(20, fieldX - rowX - 8);
                        this.labelWidget.setX(rowX + 4);
                        this.labelWidget.setY(rowY + 4);
                        this.labelWidget.setWidth(labelWidth);
                        this.labelWidget.alignLeft();
                    }
                } else {
                    int resetW = this.reset == null ? 0 : this.reset.getWidth();
                    int buttonW = Math.max(20, rowWidth - resetW - 6);
                    this.primary.setX(rowX);
                    this.primary.setY(widgetY);
                    this.primary.setWidth(buttonW);
                    if (this.reset != null) {
                        this.reset.setX(rowX + rowWidth - resetW);
                        this.reset.setY(widgetY);
                    }
                }
            }
            if (this.labelWidget != null) {
                this.labelWidget.render(context, mouseX, mouseY, delta);
            }
            for (ClickableWidget widget : this.widgets) {
                widget.render(context, mouseX, mouseY, delta);
            }
        }

        public List<? extends Selectable> selectableChildren() {
            return List.copyOf(this.widgets);
        }

        public void forEachChild(Consumer<ClickableWidget> consumer) {
            this.widgets.forEach(consumer);
        }

        public List<? extends Element> children() {
            return List.copyOf(this.widgets);
        }

        private static enum RowType {
            LABELED_FIELD,
            GROUPED_FIELD,
            BUTTON_RESET,
            SECTION_HEADER;

        }
    }
}

