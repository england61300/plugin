package com.example.core.gui.menu;

public class MenuContext {
    private int page;
    private BaseMenu previous;

    public MenuContext(int page, BaseMenu previous) {
        this.page = page;
        this.previous = previous;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public BaseMenu getPrevious() {
        return previous;
    }

    public void setPrevious(BaseMenu previous) {
        this.previous = previous;
    }
}
