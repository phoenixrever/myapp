package com.phoenixhell.app.ui.layout;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.phoenixhell.app.ui.page.Page;
import com.phoenixhell.app.ui.page.components.BreadcrumbsPage;
import com.phoenixhell.app.ui.page.components.CalendarPage;
import com.phoenixhell.app.ui.page.components.CardPage;
import com.phoenixhell.app.ui.page.components.CustomTextFieldPage;
import com.phoenixhell.app.ui.page.components.DeckPanePage;
import com.phoenixhell.app.ui.page.components.InputGroupPage;
import com.phoenixhell.app.ui.page.components.MessagePage;
import com.phoenixhell.app.ui.page.components.ModalPanePage;
import com.phoenixhell.app.ui.page.components.NotificationPage;
import com.phoenixhell.app.ui.page.components.PopoverPage;
import com.phoenixhell.app.ui.page.components.SegmentedControlPage;
import com.phoenixhell.app.ui.page.components.TabLinePage;
import com.phoenixhell.app.ui.page.components.TilePage;
import com.phoenixhell.app.ui.page.components.ToggleSwitchPage;
import com.phoenixhell.app.ui.page.general.BBCodePage;
import com.phoenixhell.app.ui.page.general.SelectableTextFlowPage;

import javafx.scene.Node;

record Nav(String title,
        @Nullable Node graphic,
        @Nullable Class<? extends Page> pageClass,
        @Nullable List<String> searchKeywords) {

    public static final Nav ROOT = new Nav("ROOT", null, null, null);

    /**
     * Java 编译器对泛型类型 Class<BBCodePage> 到 Class<? extends Page> 的推断不够灵活
     * 参数列表里的每个 .class 实际上是 Class<BBCodePage> 这种具体类型，编译器不认为它们自动是 Class<? extends
     * Page>。Java 的泛型不支持“泛型之间的协变”自动推断（不像数组那样能自动转换）。
     */
    private static final Set<Class<? extends Object>> TAGGED_PAGES = Set.of(
            BBCodePage.class,
            BreadcrumbsPage.class,
            CalendarPage.class,
            CardPage.class,
            CustomTextFieldPage.class,
            DeckPanePage.class,
            InputGroupPage.class,
            MessagePage.class,
            ModalPanePage.class,
            NotificationPage.class,
            PopoverPage.class,
            SegmentedControlPage.class,
            SelectableTextFlowPage.class,
            TilePage.class,
            TabLinePage.class,
            ToggleSwitchPage.class);

    public Nav {
        Objects.requireNonNull(title, "title");
        searchKeywords = Objects.requireNonNullElse(searchKeywords, Collections.emptyList());
    }

    public boolean isGroup() {
        return pageClass == null;
    }

    public boolean matches(String filter) {
        Objects.requireNonNull(filter);
        return contains(title, filter)
                || (searchKeywords != null && searchKeywords.stream().anyMatch(keyword -> contains(keyword, filter)));
    }

    public boolean isTagged() {
        return pageClass != null && TAGGED_PAGES.contains(pageClass);
    }

    private boolean contains(String text, String filter) {
        return text.toLowerCase().contains(filter.toLowerCase());
    }
}
