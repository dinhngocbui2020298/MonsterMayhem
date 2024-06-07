let gameId = null;
let playerId = `player${Math.floor(Math.random() * 1000)}`;

function newGame() {
    fetch('/new_game', { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            gameId = data.game_id;
            updateStatus('New game created: ' + gameId);
        });
}

function joinGame() {
    fetch('/join_game', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ game_id: gameId, player_id: playerId })
    }).then(response => response.json())
      .then(data => updateStatus('Joined game: ' + data.game_id));
}

function placeMonster(type) {
    const position = prompt('Enter position (x,y):').split(',');
    fetch('/place_monster', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            game_id: gameId,
            player_id: playerId,
            monster: type,
            position: [parseInt(position[0]), parseInt(position[1])]
        })
    }).then(response => response.json())
      .then(data => {
          if (data.status === 'placed') {
              updateGrid();
          } else {
              updateStatus('Failed to place monster: ' + data.reason);
          }
      });
}

function moveMonster() {
    const fromPos = prompt('Enter from position (x,y):').split(',');
    const toPos = prompt('Enter to position (x,y):').split(',');

    fetch('/move_monster', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            game_id: gameId,
            player_id: playerId,
            from: [parseInt(fromPos[0]), parseInt(fromPos[1])],
            to: [parseInt(toPos[0]), parseInt(toPos[1])]
        })
    }).then(response => response.json())
      .then(data => {
          if (data.status === 'moved') {
              updateGrid();
          } else {
              updateStatus('Failed to move monster: ' + data.reason);
          }
      });
}

function updateGrid() {
    fetch(`/game_state?game_id=${gameId}`)
        .then(response => response.json())
        .then(data => {
            const grid = document.getElementById('grid');
            grid.innerHTML = '';
            for (let i = 0; i < 10; i++) {
                for (let j = 0; j < 10; j++) {
                    const cell = document.createElement('div');
                    cell.className = 'cell';
                    const monster = data.grid[i][j];
                    if (monster) {
                        const [player, type] = monster.split(':');
                        cell.innerText = type[0].toUpperCase();
                    }
                    grid.appendChild(cell);
                }
            }
        });
}

function updateStatus(message) {
    const status = document.getElementById('status');
    status.innerText = 'Status: ' + message;
}
