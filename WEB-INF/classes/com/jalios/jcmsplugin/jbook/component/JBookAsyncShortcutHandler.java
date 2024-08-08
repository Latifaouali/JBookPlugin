package com.jalios.jcmsplugin.jbook.component;

import com.jalios.jcms.shortcut.AsyncShortcutHandler;
import com.jalios.jcms.shortcut.BadgeLevel;
import com.jalios.jcms.shortcut.ShortcutInfo;
import com.jalios.jcmsplugin.jbook.JBookManager;

public class JBookAsyncShortcutHandler extends AsyncShortcutHandler {
  @Override
  public ShortcutInfo getShortcutInfo() {

    int count = JBookManager.getInstance().getCurrentBorrowingCount(loggedMember);
    if (count == 0) {
      return null;
    }

    ShortcutInfo info = new ShortcutInfo();
    info.setBadgeText(String.valueOf(count));
    info.setBadgeLevel(BadgeLevel.INFO);
    info.setUrl("plugins/JBookPlugin/jsp/app/jbook.jsp?view=MY_BORROWINGS");
    return info;
  }
}