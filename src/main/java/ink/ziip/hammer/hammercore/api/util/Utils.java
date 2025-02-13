package ink.ziip.hammer.hammercore.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Utils {

    private static final PotionEffectType[] badEffects = new PotionEffectType[]{
            PotionEffectType.SLOWNESS,
            PotionEffectType.WITHER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.BLINDNESS,
            PotionEffectType.HUNGER,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.POISON,
            PotionEffectType.LEVITATION,
            PotionEffectType.LEVITATION,
            PotionEffectType.GLOWING,
            PotionEffectType.GLOWING,
    };

    private static final String[] badEffectsTranslation = new String[]{
            "迟缓",
            "凋零",
            "虚弱",
            "失明",
            "饥饿",
            "隐身",
            "隐身",
            "中毒",
            "漂浮",
            "漂浮",
            "发光",
            "发光",
    };

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

    public List<Object> getRandomPotionEffect() {
        Random random = new Random();
        int num = random.nextInt(0, badEffects.length);
        return Arrays.asList(badEffects[num], badEffectsTranslation[num]);
    }

    public static boolean isTeleportationItem(String lore) {
        String regex = "\\[[0-9]{3}-.*]";
        lore = ChatColor.stripColor(lore);
        return Pattern.matches(regex, lore);
    }
}
