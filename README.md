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

## Command map by plugin
> Current commands are marked **Live** (implemented in this repo). Planned commands are **Planned** and serve as your build roadmap.

### 1) Core (**Live**)
- `/coredebug` — Show core debug info (TPS, online players, worlds, storage provider).
- `/corereload` — Reload core configs and services.

### 2) Database / Storage System (inside Core for now)
- No direct player command needed right now.
- `/coredebug` — Lets admins verify the active storage backend.

### 3) GUI/Menu Framework (inside Core for now)
- No direct end-user command by design; this is an internal framework used by other plugin menus.

### 4) Economy Core (**Live/Started**)
- `/balance` — Show your current balance.
- `/money add <player> <amount> [currency]` — Admin add money.
- `/money remove <player> <amount> [currency]` — Admin remove money.
- `/money set <player> <amount> [currency]` — Admin set money.

### 5) Item/Reward Framework (**Planned**)
- `/rewards` — Open rewards admin/player viewer.
- `/reward give <player> <rewardId>` — Give a configured reward.
- `/reward table test <tableId> [rolls]` — Test random reward tables.

### 6) Spawn / Hub / Teleport Plugin (**Planned**)
- `/setspawn` — Set global spawn.
- `/spawn` — Teleport to spawn.
- `/hub` — Return to hub.
- `/warp`, `/warp set <name>`, `/warp del <name>` — Warp management.
- `/rtp` — Random teleport.
- `/tpa <player>`, `/tpahere <player>`, `/tpaccept`, `/tpdeny` — Teleport requests.
- `/pwarp`, `/pwarp set <name>`, `/pwarp del <name>` — Player warps.

### 7) Custom Shop Plugin (**Planned**)
- `/shop` — Open shop UI.
- `/shop sell` — Sell item in hand or selection.
- `/shopadmin` — Open shop editor.

### 8) Crates Plugin (**Planned**)
- `/crates` — Open crate menu.
- `/crate open <type>` — Open crate if key exists.
- `/crate key give <player> <type> <amount>` — Give crate keys.
- `/crate preview <type>` — Preview rewards.

### 9) Daily / Login Rewards (**Planned**)
- `/daily` — Claim daily reward.
- `/rewards` — Open reward streak menu.
- `/vote` — Trigger/manual vote reward check.
- `/kit <name>` — Claim configured kit.

### 10) Player Utility Plugin (**Planned**)
- `/ec` — Open your ender chest.
- `/craft` — Open crafting table.
- `/trash` — Open trash GUI.
- `/cooldowns` — View personal command cooldowns.

### 11) Auction House / Player Market (**Planned**)
- `/ah` — Open auction house.
- `/ah sell <price>` — List held item.
- `/ah cancel <listingId>` — Cancel listing.
- `/ah search <text>` — Search listings.

### 12) Coin Flip / Gambling / RPS (**Planned**)
- `/coinflip create <amount>` — Create coinflip bet.
- `/coinflip accept <id>` — Accept bet.
- `/coinflip list` — View open bets.
- `/rps challenge <player> <amount>` — Start rock-paper-scissors bet.

### 13) Player Trading Plugin (**Planned**)
- `/trade <player>` — Send trade request.
- `/trade accept` — Accept request.
- `/trade deny` — Deny request.
- `/trade cancel` — Cancel active trade.

### 14) Advanced Currency / Tokens (**Planned**)
- `/tokens` — Show token balance.
- `/tokens pay <player> <amount>` — Transfer tokens.
- `/tokens convert <from> <to> <amount>` — Currency conversion.

### 15) Envoy Plugin (**Planned**)
- `/envoy` — Show envoy status.
- `/envoy start` — Force start envoy.
- `/envoy loot <tableId>` — Preview/edit envoy loot table.

### 16) Fishing Tournament Plugin (**Planned**)
- `/fish tournament` — Tournament status.
- `/fish top` — Fishing leaderboard.
- `/fish join` — Join current tournament.

### 17) Boss Event Plugin (**Planned**)
- `/boss` — Boss event status.
- `/boss start <id>` — Force spawn a boss.
- `/boss top` — Damage leaderboard.

### 18) KOTH / Area Control Plugin (**Planned**)
- `/koth` — Current KOTH info.
- `/koth start <arena>` — Start KOTH.
- `/koth create <name>` — Create arena.

### 19) Mini Event Scheduler (**Planned**)
- `/events` — List scheduled events.
- `/events next` — Show next event.
- `/events forcestart <eventId>` — Force start an event.

### 20) Spawner Plugin (**Planned**)
- `/spawner` — Open spawner GUI.
- `/spawner give <player> <type> [amount]` — Give spawner items.
- `/spawner upgrade` — Upgrade targeted spawner.

### 21) Custom Enchants Plugin (**Planned**)
- `/ce` — Open custom enchant menu.
- `/ce give <player> <enchant> <level>` — Give enchant book.
- `/ce list` — List available enchants.

### 22) Dungeon Plugin (**Planned**)
- `/dungeon` — Dungeon status/menu.
- `/dungeon start <id>` — Start dungeon run.
- `/dungeon leave` — Leave run.

### 23) Mob / Elite System (**Planned**)
- `/elite` — Elite mob status.
- `/elite spawn <type>` — Spawn test elite mob.
- `/elite loot <type>` — Loot table viewer.

### 24) Progression / Season Pass Plugin (**Planned**)
- `/pass` — Open season pass.
- `/missions` — View current missions.
- `/pass claim <tier>` — Claim tier reward.

### 25) Admin Tools Plugin (**Planned**)
- `/inspect <player>` — Inspect inventory.
- `/systemlogs <player>` — View plugin action logs.
- `/forcestart <event>` — Force start supported event.

### 26) Logging / Audit Plugin (**Planned**)
- `/audit player <name>` — View player audit timeline.
- `/audit money <name>` — View money log entries.
- `/audit export <type>` — Export logs.

### 27) Placeholder / API Bridge (**Planned**)
- `/placeholders` — List available placeholders.
- `/api reload` — Reload placeholder/API bridge cache.

### 28) Leaderboards Plugin (**Planned**)
- `/top money` — Balance top.
- `/top fish` — Fish leaderboard.
- `/top boss` — Boss damage leaderboard.

### 29) Config/Editor Tools (**Planned**)
- `/editor` — Open admin editor hub.
- `/editor loot <tableId>` — Edit loot table.
- `/editor validate` — Validate loaded configs.

### 30) Land Claim (**Planned**)
- `/claim` — Claim current chunk/area.
- `/unclaim` — Remove claim.
- `/claims` — View/manage claims.
- `/claim settings` — Open claim settings GUI.

### 31) Player Death Punishment (**Planned**)
- `/deathpunish` — View rules and settings.
- `/deathpunish toggle` — Toggle system (admin).

### 32) Stats Based on Money (**Planned**)
- `/moneybonus` — View stat bonuses from wealth.
- `/moneybonus gui` — Open wealth-stat progression GUI.

### 33) Jobs (**Planned**)
- `/jobs` — Open jobs menu.
- `/jobs join <job>` — Join a job.
- `/jobs leave <job>` — Leave a job.
- `/jobs stats` — Show job levels/progress.

## Build
```bash
mvn clean package
```


## Planned plugin modules (separate builds)
All planned plugins now have independent Maven scaffolds under `plugins/`, each with its own `pom.xml`, `plugin.yml`, main class, and README. Build any module individually from its folder.

See: `plugins/README.md`.
