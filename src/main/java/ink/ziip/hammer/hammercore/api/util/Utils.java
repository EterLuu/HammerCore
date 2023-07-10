package ink.ziip.hammer.hammercore.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Utils {

    public static String translateColorCodes(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static HashMap<String, String> splitList(List<String> list) {
        HashMap<String, String> map = new HashMap<>();

        list.forEach(
                content -> {
                    String[] splits = content.split(":", 2);
                    map.put(splits[0], splits[1]);
                });

        return map;
    }
}
