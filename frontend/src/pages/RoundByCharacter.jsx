import HeaderPage from "../components/HeaderPage";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SelectType from "../components/SelectType";
import Base from "../components/Base";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import SubmitButton from "../components/SubmitButton";
import EndGame from "../components/EndGame";
import AIPopUP from '../components/AIPopUP';
import LoadingSpinner from "../components/LoadingSpinner";

function RoundByCharacter() {
  // ------------------------ Nav -------------------//
  const navigate = useNavigate();

  // ---------------- Location --------------------//
  const location = useLocation();
  const characters = location.state?.characters || [];
  const rounds = location.state?.rounds || [];
  const [userData, setUserData] = useState(JSON.parse(sessionStorage.getItem("user")));

  useEffect(() => {
    const storedUser = JSON.parse(sessionStorage.getItem("user"));
    if (storedUser) {
      setUserData(storedUser);
    }
  }, [sessionStorage.getItem("user")]);

  // ---------------- Options ----------------------//
  const [gameOver, setGameOver] = useState(false);
  const [currentRound, setCurrentRound] = useState(0);
  const currentRoundData = characters[currentRound] || []; 
  const [currentImage, setCurrentImage] = useState("");
  const [timeLeft, setTimeLeft] = useState(90);
  const [gameResult, setGameResult] = useState(""); 
  const [tipAI, setTipAI] = useState(false);
  const [summarizedText, setSummarizedText] = useState("");
  const [currentSynopsis, setCurrentSynopsis] = useState("");
  const [pauseGame, setPauseGame] = useState(false);
  const [isLoading, setIsLoading] = useState(false); 

  // ---------------- Error ----------------------//
  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();

  // ----------------- Effects -------------------//
  useEffect(() => {
    if (currentRoundData && Array.isArray(currentRoundData)) {
      currentRoundData.forEach((pair) => {
        if (pair.r) {
          setCurrentImage(pair.l.image);
          if (typeof pair.l.synopsis === "string") {
            let new_synopsis = pair.l.synopsis
              .replace(/\n/g, " ") 
              .replace(/"/g, "")   
              .trim();             
            setCurrentSynopsis(new_synopsis);
          } else {
            setCurrentSynopsis(""); 
          }
        } else {
          setCurrentImage(null);
          setCurrentSynopsis("");
        }
      });
    }
    setTimeLeft(90);
  }, [currentRound]);

  useEffect(() => {
    if (pauseGame){
      return;
    }

    if (timeLeft === 0) {
      setGameResult("lose"); 
      setGameOver(true);
      updateUserPoints();
      return;
    }

    const timer = setInterval(() => {
      setTimeLeft((prevTime) => prevTime - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [timeLeft,pauseGame, currentRound]);

  // ---------------- Functions ------------------//
  const selectOption = (option) => {
    if (!option) {
      setGameResult("lose"); 
      setGameOver(true);
      return;
    }

    // Check if is final round
    if (currentRound + 1 >= rounds) {
      setGameResult("win"); 
      setGameOver(true);
      return;
    }

    const timeoutId = setTimeout(() => {
      setCurrentRound(currentRound + 1);
    }, 1000);

    return () => clearTimeout(timeoutId);
  };

  const updateUserPoints = async () => {
    const points = gameResult === "win" ? 25 : -10;
    console.log(`Enviando pontuação: ${points} para o usuário ${userData.id}`);
    try {
      const response = await fetch("http://localhost:5050/ranking/verificationPoints", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ id: userData.id, quantity: points }),
      });

      if (!response.ok) {
        const error = await response.json();
        setErrorMessage(error.message);
        openAlert();
        return;
      }

      const data = await response.json();
      setUserData(data.user);
      sessionStorage.setItem("user", JSON.stringify(data.user));
    } catch (error) {
      setErrorMessage(error.message);
      openAlert();
    }
  };

  const restartGame = async () => {
    updateUserPoints();
    setGameOver(false);
    setCurrentRound(0);
    setTimeLeft(90);
    setGameResult("");
  };

  const exitGame = () => {
    updateUserPoints();
    navigate("/"); 
  };

  const closePopUp = () => {
    setTipAI(false);
    setSummarizedText("");
    setPauseGame(false);
  };

  const helpByIA = async () => {
    if (!currentSynopsis) {
      setErrorMessage("Synopsis not available");
      openAlert();
      return;
    }
    setPauseGame(true);
    setIsLoading(true);
    
    try {
      const response = await fetch("http://localhost:5050/byCharacter/help", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          synopsis: currentSynopsis,
        }),
      });

      if (!response.ok) {
        const error = await response.json();
        setErrorMessage(error.message);
        openAlert();
        return;
      }

      const data = await response.json();
      setSummarizedText(data.new_text);
      setTipAI(true);
      
    } catch (error) {
      setErrorMessage(error.message);
      openAlert();
    }
    finally {
      setIsLoading(false); 
  }
  };

  return (
    <div className="flex flex-col items-center justify-between space-y-4 h-screen">
      <HeaderPage userData={userData} setUserData={setUserData} />
      {isLoading && (
        <>
          <div className="absolute inset-0 bg-black bg-opacity-50 z-40"></div>
          <LoadingSpinner />
        </>
      )} 

      {!tipAI ? (
        <>
          <SubmitButton onClick={helpByIA}>
            Help
          </SubmitButton>

          <div className="flex flex-col justify-center items-center space-y-8">
            <div className="w-[500px] h-[300px] bg-white rounded-3xl border-2 border-black-200">
              <img
                className="w-[100%] h-[100%] rounded-3xl"
                src={currentImage}
                alt="char-image"
              />
            </div>

            <div className="w-[600px] h[120px] flex flex-wrap justify-center gap-2 ">
              {!gameOver ? (
                currentRoundData.map((pair, index) => (
                  <SelectType key={index} onClick={() => selectOption(pair.r)}>
                    {pair.l.name}
                  </SelectType>
                ))
              ) : (
                <EndGame
                  text={gameResult === "win" ? "You win!" : "You lose!"}
                  onTryAgain={restartGame}
                  onExit={exitGame}
                />
              )}
            </div>
          </div>
        </>
      ) : (
        <AIPopUP summarizedText={summarizedText} closePopUp={closePopUp} />
      )}

      {/* GameOver PopUp */}
      {(gameOver || tipAI) && (
        <div className="absolute inset-0 bg-black bg-opacity-50 z-10"></div>
      )}

      <Base round={`Round: ${currentRound + 1}`} time={timeLeft} />
      {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default RoundByCharacter;
