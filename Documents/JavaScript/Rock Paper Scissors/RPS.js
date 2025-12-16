
const outputBox = document.getElementById("output-box");

const rockButton = document.getElementById("rock");
const paperButton = document.getElementById("paper");
const scissorsButton = document.getElementById("scissors");

const rockImg = document.querySelector(".rockimg");
const paperImg = document.querySelector(".paperimg");
const scissorsImg = document.querySelector(".scissorsimg");

let wins = 0;
let loss = 0;
let tie = 0;
let rounds = 0;

let winCount = document.getElementById("win");
let loseCount = document.getElementById("lose");
let tieCount = document.getElementById("tie");

const rpsList = ["rock", "paper", "scissors"];

rockButton.addEventListener("click", () => {
    gameLogic("rock");
});

paperButton.addEventListener("click", () => {
    gameLogic("paper");
});

scissorsButton.addEventListener("click", () => {
    gameLogic("scissors");
});

rockButton.addEventListener("click", () => {
    rockImg.classList.add("bounce");
    setTimeout(() => rockImg.classList.remove("bounce"), 500);
});

paperButton.addEventListener("click", () => {
    paperImg.classList.add("bounce");
    setTimeout(() => paperImg.classList.remove("bounce"), 500);
});

scissorsButton.addEventListener("click", () => {
    scissorsImg.classList.add("bounce");
    setTimeout(() => scissorsImg.classList.remove("bounce"), 500);
});


function gameLogic(playerChoice) {

    const computerChoice = rpsList[Math.floor(Math.random() * rpsList.length)];

    if (playerChoice == "rock" && computerChoice == "scissors") {
        outputBox.textContent = "Computer picks scissors, you win!";
        wins++;
        rounds++;
    }

    else if (playerChoice == "paper" && computerChoice == "rock") {
        outputBox.textContent = "Computer picks rock, you win!";
        wins++;
        rounds++;
    }

    else if (playerChoice == "scissors" && computerChoice == "paper") {
        outputBox.textContent = "Computer picks paper, you win!";
        wins++;
        rounds++;
    }

    else if (playerChoice == computerChoice) {
        outputBox.textContent = "Computer picks " + computerChoice + " you tie!";
        tie++;
        rounds++;
    }

    else {
        outputBox.textContent = "Computer picks " + computerChoice + " you lose!";
        loss++;
        rounds++;
    }

    winCount.textContent = "Wins " + wins;
    loseCount.textContent = "Losses " + loss;
    tieCount.textContent = "Ties " + tie;

    if (rounds == 5) {
        if (wins > loss && wins > tie) {
            outputBox.textContent = "You win!";
            wins = 0;
            loss = 0;
            tie = 0;
            rounds = 0;
        } else if (loss > wins && loss > tie) {
            outputBox.textContent = "You lose!";
            wins = 0;
            loss = 0;
            tie = 0;
            rounds = 0;
        } else {
            outputBox.textContent = "You tied!";
            wins = 0;
            loss = 0;
            tie = 0;
            rounds = 0;
        }
    }
}




