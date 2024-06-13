// Establish WebSocket connection
const socket = new WebSocket("ws://localhost:8080/score");

socket.onopen = (event) => {
    console.log("WebSocket connection opened:", event);
};

socket.onmessage = (event) => {
    const data = JSON.parse(event.data);

    // Check if the data is live match data
    if (data.result && data.result.length > 0) {
        const matchData = data.result[0];
        // Render live match data
        renderLiveMatchData(matchData);
        // Render statistics
        renderPlayerStatistics(matchData.statistics);
        renderCards(matchData.cards);
        renderGoalscorers(matchData.goalscorers);
        // Render chart
        renderGoalscorersChart(matchData.goalscorers);
    }
};

function renderLiveMatchData(matchData) {
    const liveMatchDataElement = document.getElementById("live-match-data");
    const homegoal = matchData.goalscorers[matchData.goalscorers.length - 1].score.split(' - ')[0];
    const awaygoal = matchData.goalscorers[matchData.goalscorers.length - 1].score.split(' - ')[1];
    liveMatchDataElement.innerHTML = `
        <center><h2>${matchData.event_home_team} <small class="text-muted">(HOME)</small> vs. ${matchData.event_away_team} <small class="text-muted">(AWAY)</small></h2></center>
        <center><h2>Score : ${homegoal} - ${awaygoal}</h2></center>
        <hr>
        <dl class="row">
          <dt class="col-sm-3">Date:</dt>
          <dd class="col-sm-9">${matchData.event_date}</dd>
          <dt class="col-sm-3">Time:</dt>
          <dd class="col-sm-9">${matchData.event_time}</dd>
          <dt class="col-sm-3">Stadium:</dt>
          <dd class="col-sm-9">${matchData.event_stadium}</dd>
         </dl>
    `;
}

function renderPlayerStatistics(statistics) {
    const playerStatisticsElement = document.getElementById("statistics");
    playerStatisticsElement.innerHTML = `
        <h3>Statistics</h3>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Type</th>
                    <th>Home</th>
                    <th>Away</th>
                </tr>
            </thead>
            <tbody>
                ${statistics.map(statistic => `
                    <tr>
                        <td>${statistic.type}</td>
                        <td>${statistic.home}</td>
                        <td>${statistic.away}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

function renderGoalscorers(goalscorers) {
    const goalscorersElement = document.getElementById("goalscorers");
    goalscorersElement.innerHTML = `
        <h3>Goalscorers</h3>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Time</th>
                    <th>Home Scorer</th>
                    <th>Score</th>
                    <th>Away Scorer</th>
                </tr>
            </thead>
            <tbody>
                ${goalscorers.map(goal => `
                    <tr>
                        <td>${goal.time}</td>
                        <td>${goal.home_scorer}</td>
                        <td>${goal.score}</td>
                        <td>${goal.away_scorer}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

function renderCards(cards) {
    const cardsElement = document.getElementById("cards");
    cardsElement.innerHTML = `
        <h3>Cards</h3>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Time</th>
                    <th>Home Fault</th>
                    <th>Card</th>
                    <th>Away Fault</th>
                </tr>
            </thead>
            <tbody>
                ${cards.map(card => `
                    <tr>
                        <td>${card.time}</td>
                        <td>${card.home_fault}</td>
                        <td>${card.card}</td>
                        <td>${card.away_fault}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

let goalscorersChart = null;

function renderGoalscorersChart(goalscorers) {
    const homegoals = goalscorers[goalscorers.length - 1].score.split(' - ')[0];
    const awaygoals = goalscorers[goalscorers.length - 1].score.split(' - ')[1];
    const labels = ['Home vs Away']
    const ctx = document.getElementById("goalscorers-chart").getContext("2d");

    // Destroy the old chart if it exists
    if (goalscorersChart !== null) {
        goalscorersChart.destroy();
    }
    goalscorersChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Home Team Goals',
                data: homegoals,
                backgroundColor: 'rgba(54, 162, 235, 0.5)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1,
            },
            {
                label: 'Away Team Goals',
                data: awaygoals,
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1,
            }]
        },
        options: {
            plugins: {
                       title: {
                           display: true,
                           text: 'Goals Home vs Away', // Your chart title goes here
                           fontSize: 48, // Adjust the font size as needed
                           fontColor: 'black' // Customize the font color
                       }
                   },
            scales: {
                x: {
                    beginAtZero: true,
                }
            }
        }
    });
}