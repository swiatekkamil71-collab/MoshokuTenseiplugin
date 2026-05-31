# Mushoku Tensei Magic Plugin

**A comprehensive magic system plugin for Paper 1.21.4 inspired by the anime Mushoku Tensei**

## Features

✨ **Advanced Magic System**
- 12 different magic schools to master
- 7 magic ranks from Beginner to God
- Progressive spell learning system
- Massive mana reserves that grow to absurd levels

🔥 **Magic Schools**
- Fire Magic (Fireball → God Flame Cataclysm)
- Water Magic (Water Bolt → Tsunami Slash)
- Earth Magic (Coming Soon)
- Wind Magic (Coming Soon)
- Healing Magic (Coming Soon)
- Divine Magic (Coming Soon)
- Lightning Magic (Coming Soon)
- Ice Magic (Coming Soon)
- Summoning Magic (Coming Soon)
- Barrier Magic (Coming Soon)
- Gravity Magic (Coming Soon)
- Advanced Arcane Magic (Coming Soon)

💪 **Progression System**
- Character levels with experience
- Magic level progression
- Combat level progression
- School-specific leveling
- Silent casting system that improves with level
- Specialization bonuses

⚡ **Mana System**
- Dynamic mana pool starting at 100
- Grows through progression to 100,000+
- Active regeneration
- Mana bar in action bar
- Customizable mana costs per spell

🎯 **Player Features**
- `/mpanel` - Main character panel with all statistics
- `/spellbook` - Spell discovery and management
- Character stats tracking
- Artifact system
- Title system
- Cast speed improvements

⚙️ **Admin Features**
- `/madmin` - Comprehensive admin panel (OP only)
- Player stat management
- Magic level control
- Mana management
- Spell unlocking
- Specialization setting
- Artifact giving
- Title assignment
- Infinite mana toggle
- Cooldown removal
- Cast speed modification
- Character reset
- Boss spawning
- Experience control

## Installation

1. Build the plugin: `mvn clean package`
2. Place the JAR in your server's `plugins` folder
3. Restart the server
4. All player data is automatically saved in `plugins/MoshokuTenseiplugin/playerdata/`

## Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/mpanel` | moshoku.use | Open your character panel |
| `/spellbook` | moshoku.use | View your spellbook |
| `/madmin` | moshoku.admin (OP) | Open admin panel |
| `/manaregen <player> <amount>` | moshoku.admin (OP) | Set player's mana regen |

## Permissions

- `moshoku.use` - Use the magic system (default: true)
- `moshoku.admin` - Admin commands (default: op)

## Technical Details

- **Java**: 21
- **Minecraft**: 1.21.4 (Paper)
- **Storage**: Binary serialization (playerdata folder)
- **Architecture**: Modular with separate managers for Players, Mana, and Spells

## Credits

Created by: **axtr_a**
Inspired by: **Mushoku Tensei** anime

## Version

**v1.0.0** - Initial Release

---

*Become the legendary mage you were destined to be!* 🔥