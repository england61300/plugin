# CorePlugin (v26.1.2)

Base Paper/Spigot plugin that provides shared infrastructure for a larger plugin suite.

## Included systems
- Plugin startup/shutdown lifecycle
- Config + language file loading
- Permission key mapping
- Storage manager (YAML live, SQL scaffolding for later)
- Economy core (balances, admin money commands, transaction logging, placeholders, multi-currency-ready)
- Utility helpers
- Command registration helper
- Admin debug command
- Reload command
- Hook manager for future integrations
- Reusable GUI/menu framework (click handling, pagination, back buttons, confirm/cancel, item builder, refresh)

## Storage system
Current default provider is **YAML** and stores per-player files with:
- identity (last known name)
- economy balances map (multi-currency)
- stats map
- progress map
- cooldowns map

Storage type can be switched in `config.yml`:
- `yaml` (implemented)
- `sqlite` (scaffold)
- `mysql` (scaffold)

## Economy core
- `/balance` for players
- `/money add|remove|set <player> <amount> [currency]` for admins
- Transaction file: `plugins/CorePlugin/logs/economy-transactions.log`
- Placeholder resolver API for `%core_balance%` and `%core_balance_<currency>%`

## GUI framework
Use `GuiManager` with `BaseMenu` / `PaginatedMenu` / `ConfirmCancelMenu` to build reusable inventories.

Key pieces:
- `MenuButton`: clickable item + action
- `MenuContext`: page + previous menu data
- `ItemBuilder`: fluent item creation
- Built-in navigation in `PaginatedMenu` (previous/next/back)
- `GuiManager.refresh(player)` for menu update logic

## Build
```bash
mvn clean package
```
