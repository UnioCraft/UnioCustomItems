package me.uniodex.uniocustomitems.managers;

import me.uniodex.uniocustomitems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    @SuppressWarnings("unused")
    private CustomItems plugin;

    public ItemManager(CustomItems plugin) {
        this.plugin = plugin;
    }

    public boolean isItemNamed(ItemStack item) {
        if (item == null) return false;
        if (item.getItemMeta() == null) return false;
        if (item.getItemMeta().getDisplayName() == null) return false;
        return true;
    }

    public ItemStack getItem(Items type) {
        if (type == Items.satisAsasi) {
            ItemStack item = new ItemStack(Material.GOLD_HOE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Satış Asası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Bu asa ile bir sandığa sağ", ChatColor.GRAY + "tıkladığınızda sandığın içindeki", ChatColor.GRAY + "eşyalar sunucu marketine satılır.", " "));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.tamirAsasi) {
            ItemStack item = new ItemStack(Material.GOLD_AXE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Tamir Asası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Bu asa ile bir sandığa sağ", ChatColor.GRAY + "tıkladığınızda sandığın içindeki", ChatColor.GRAY + "tüm eşyalar tamir edilir.", " "));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.satisSandigi) {
            ItemStack item = new ItemStack(Material.CHEST);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + "Satış Sandığı");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Bu sandığın içine giren", ChatColor.GRAY + "eşyalar otomatik olarak", ChatColor.GRAY + "sunucu marketine satılır.", " "));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.x200) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "200 x 200 Ada Genişletici " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Adanı genişletmek için sağ tıkla!"));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.x300) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "300 x 300 Ada Genişletici " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Adanı genişletmek için sağ tıkla!"));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.x400) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "400 x 400 Ada Genişletici " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Adanı genişletmek için sağ tıkla!"));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.x500) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "500 x 500 Ada Genişletici " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Adanı genişletmek için sağ tıkla!"));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.ucusKarti) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Uçuş Kartı " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Sınırsız uçuş hakkı kazanmak için sağ tıkla!"));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.buyuluAlanKazmasi) {
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 10, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 8, true);
            meta.setDisplayName(ChatColor.RED + "Büyülü Alan Kazması");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Bu kazmayla blok kırdığınızda", ChatColor.GRAY + "çevresindeki 3x3'lük alandaki", ChatColor.GRAY + "tüm blokları yok eder.", " ", ChatColor.GRAY + "Bu kazmanın nasıl yapıldığı", ChatColor.GRAY + "tam olarak bilinmese de", ChatColor.GRAY + "kadim ırk kalıntısı olduğuna", ChatColor.GRAY + "dair rivayetler mevcuttur."));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.vaghlodarKazmasi) {
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
            meta.addEnchant(Enchantment.DURABILITY, 8, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
            meta.setDisplayName(ChatColor.DARK_PURPLE + "Vaghlodar Kazması");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Bu kazma, karanlık " + ChatColor.DARK_PURPLE + "Vaghlodar", ChatColor.RED + "dağlarında kara büyüyle hapsedilen", ChatColor.RED + "yakutları çıkarmak için dünyanın en", ChatColor.RED + "becerikli demir işlemecileri tarafından", ChatColor.RED + "yapıldı."));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.cilaliElmasKilic) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DAMAGE_ALL, 8, true);
            meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 6, true);
            meta.addEnchant(Enchantment.FIRE_ASPECT, 4, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 6, true);
            meta.setDisplayName(ChatColor.GREEN + "Cilalı Elmas Kılıç");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Özel efsunlar ve yağlarla", ChatColor.RED + "bezenmiş bir elmas kılıç."));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.buyuluLavanorYayi) {
            ItemStack item = new ItemStack(Material.BOW);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 8, true);
            meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 3, true);
            meta.addEnchant(Enchantment.ARROW_FIRE, 2, true);
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            meta.setDisplayName(ChatColor.GOLD + "Büyülü Lavanor Yayı");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Lavanor yapımı büyülü yay.", " ", ChatColor.RED + "Bir elf şehri olan Lavanor", ChatColor.RED + "okçuluk konusunda adını", ChatColor.RED + "yedi diyarda duyurmuştur.", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.karaBuyuluVaghlodarKazmasi) {
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 15, true);
            meta.addEnchant(Enchantment.DURABILITY, 10, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 6, true);
            meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kara Büyülü Vaghlodar Kazması");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Vaghlodar Kazması'nın cadılar şehri.", ChatColor.RED + "Saffron'da kara büyüyle güçlendirilmiş", ChatColor.RED + "bir kopyası. Yapımında çok masumun", ChatColor.RED + "kanının döküldüğü rivayet edilir."));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.kemikkiraninBaltasi) {
            ItemStack item = new ItemStack(Material.DIAMOND_AXE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
            meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 8, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
            meta.addEnchant(Enchantment.DURABILITY, 10, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
            meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kemikkıran'ın Baltası");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Yedi diyarın en tehlikeli.", ChatColor.RED + "bosslarından biri olan Kemikkıran'ın", ChatColor.RED + "çocuklarına yadigar bıraktığı balta.", " ", ChatColor.RED + "Sadece bu baltaya dokunmak için", ChatColor.RED + "canını vermiş şövalyeler vardır."));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.efsunlanmisElmasKask) {
            ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.setDisplayName(ChatColor.BLUE + "Efsunlanmış Elmas Kask");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.efsunlanmisElmasZirh) {
            ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.setDisplayName(ChatColor.BLUE + "Efsunlanmış Elmas Zırh");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.efsunlanmisElmasPantolon) {
            ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.setDisplayName(ChatColor.BLUE + "Efsunlanmış Elmas Pantolon");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.efsunlanmisElmasBot) {
            ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.setDisplayName(ChatColor.BLUE + "Efsunlanmış Elmas Bot");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.nadirKorumaEfsunu) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            meta.setDisplayName(ChatColor.GOLD + "Nadir Koruma Efsunu");
            meta.setLore(Arrays.asList(" ", ChatColor.RED + "Bu efsunu örs ile", ChatColor.RED + "kullanarak eşyalarınıza", ChatColor.RED + "Koruma 5 büyüsü", ChatColor.RED + "ekleyebilirsiniz!", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.ganimetKilici) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);
            meta.setDisplayName(ChatColor.GOLD + "Ganimet Kılıcı");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.demircininRuyaKazmasi) {
            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
            meta.addEnchant(Enchantment.DURABILITY, 4, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 4, true);
            meta.setDisplayName(ChatColor.BLUE + "Demircinin Rüya Kazması");
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.pembepenceYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Pempepençe " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Pembepençe'nin hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " ", ChatColor.GRAY + "Hemen çağır ve onun yozlaşmış", ChatColor.GRAY + "ruhunu yok et!", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.kemikkiranYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Kemikkıran " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Kemikkıran'ın hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " ", ChatColor.GRAY + "DİKKAT! Kemikkıran oyundaki en", ChatColor.GRAY + "güçlü yaratıklardan biridir", ChatColor.GRAY + "ve tek başına öldürmen imkansız olabilir!", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.yikimgetirenYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Yıkımgetiren " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Yıkımgetiren'in hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " ", ChatColor.GRAY + "DİKKAT! Yıkımgetiren oyundaki en", ChatColor.GRAY + "güçlü yaratıklardan biridir", ChatColor.GRAY + "ve tek başına öldürmen imkansız olabilir!", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.vahsiavciYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Vahşi Avcı " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Vahşi Avcı'nın hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " ", ChatColor.GRAY + "DİKKAT! Vahşi Avcı oyundaki en", ChatColor.GRAY + "güçlü yaratıklardan biridir", ChatColor.GRAY + "ve tek başına öldürmen imkansız olabilir!", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.yarimbeyinYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Yarım Beyin " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Yarım Beyin'in hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.karacellatYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Kara Cellat " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Kara Cellat'ın hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.demirgovdeYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Demir Gövde " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Demir Gövde'nin hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.cehennemtazisiYumurta) {
            ItemStack item = new ItemStack(Material.MONSTER_EGG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Cehennem Tazısı " + ChatColor.WHITE + "Boss Yumurtası");
            meta.setLore(Arrays.asList(" ", ChatColor.GRAY + "Cehennem Tazısı'nın hapsolmuş ruhu", ChatColor.GRAY + "bu yumurtanın içinde yatıyor.", " "));
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.vipKarti) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "VIP Kartı " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Hesabına 1 aylık VIP üyelik", ChatColor.GRAY + "etkinleştirmek için sağ tıkla."));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.vipPlusKarti) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "VIP+ Kartı " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Hesabına 1 aylık VIP+ üyelik", ChatColor.GRAY + "etkinleştirmek için sağ tıkla."));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.uvipKarti) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "UVIP Kartı " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Hesabına 1 aylık UVIP üyelik", ChatColor.GRAY + "etkinleştirmek için sağ tıkla."));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        if (type == Items.uvipPlusKarti) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "UVIP+ Kartı " + ChatColor.GRAY + "(Sağ Tıkla)");
            meta.setLore(Arrays.asList(ChatColor.GRAY + "Hesabına 1 aylık UVIP+ üyelik", ChatColor.GRAY + "etkinleştirmek için sağ tıkla."));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            return item;
        }

        return null;
    }

    @Deprecated
    public void giveSpawner(Player p, String spawner) {
        String esName = null;

        switch (spawner) {
            case "golem":
                esName = "Iron_Golem";
                break;
            case "zombi":
                esName = "Zombie";
                break;
            case "iskelet":
                esName = "Skeleton";
                break;
            case "orumcek":
                esName = "Spider";
                break;
            case "zombidomuzadam":
                esName = "Pig_Zombie";
                break;
            case "balcik":
                esName = "Slime";
                break;
            case "inek":
                esName = "Cow";
                break;
            case "tavuk":
                esName = "Chicken";
                break;
            case "koyun":
                esName = "Sheep";
                break;
            case "domuz":
                esName = "Pig";
                break;
            case "at":
                esName = "Horse";
                break;
            case "moontar":
                esName = "Mushroom_Cow";
                break;
            case "koylu":
                esName = "Villager";
                break;
            case "demir":
                esName = "Custom_1";
                break;
        }

        if (esName != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "es give " + p.getName() + " " + esName + " 1 1");
        }
    }

    public void giveHopper(Player p, String hopper) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eh give " + p.getName() + " " + hopper);
    }

    public enum Items {
        satisAsasi, tamirAsasi, satisSandigi, x200, x300, x400, x500, ucusKarti, buyuluAlanKazmasi,
        vipKarti, vipPlusKarti, uvipKarti, uvipPlusKarti,
        vaghlodarKazmasi, cilaliElmasKilic, buyuluLavanorYayi, karaBuyuluVaghlodarKazmasi, kemikkiraninBaltasi,
        demircininRuyaKazmasi,
        nadirKorumaEfsunu,
        pembepenceYumurta, kemikkiranYumurta, yikimgetirenYumurta, vahsiavciYumurta,
        yarimbeyinYumurta, karacellatYumurta, demirgovdeYumurta, cehennemtazisiYumurta,
        efsunlanmisElmasKask, efsunlanmisElmasZirh, efsunlanmisElmasPantolon, efsunlanmisElmasBot,
        ganimetKilici;

    }
}
