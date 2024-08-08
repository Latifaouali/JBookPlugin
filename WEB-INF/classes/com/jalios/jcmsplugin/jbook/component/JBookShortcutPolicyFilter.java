package com.jalios.jcmsplugin.jbook.component;

import com.jalios.jcms.Channel;
import com.jalios.jcms.shortcut.AbstractShortcut;
import com.jalios.jcms.shortcut.BasicShortcutPolicyFilter;

public class JBookShortcutPolicyFilter extends BasicShortcutPolicyFilter {

  @Override
  public String getShortcutInfoAsync(AbstractShortcut shortcut) {
    if (shortcut != Channel.getChannel().getData("$id.shortcut.jcmsplugin.jbook")) {
      return null;
    }
    return "plugins/JBookPlugin/jsp/app/jbookShortcutInfo.jsp";
  }
}
