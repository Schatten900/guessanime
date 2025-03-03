import HeaderPage from "../components/HeaderPage";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SelectType from "../components/SelectType";
import Base from "../components/Base";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import SubmitButton from "../components/SubmitButton";

function RoundByCharacter(props) {
  // ---------------- Character --------------------//
  const location = useLocation();
  const characters = location.state?.characters || [];
  const userData = location.user?.userData || [];


  // ---------------- Options ----------------------//
  const [gameOver, setGameOver] = useState(false); 
  const [currentRound,setCurrentRound] = useState(0);
  const currentRoundData = characters[currentRound];
  const [currentImage,setCurrentImage] = useState(null);
  const [timeLeft,setTimeLeft] = useState(30);

  // ---------------- Error ----------------------//
  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();


  // ----------------- Effects -------------------//

  useEffect(()=>{
    currentRoundData.map((pair, index) => (
      pair.r ? setCurrentImage(pair.l.image) : null
    ))
    setTimeLeft(30);
  }, [currentRound])

  useEffect(() => {
    
    if (timeLeft === 0) {
      setCurrentRound(0);
      return;
    }

    const timer = setInterval(() => {
      setTimeLeft((prevTime) => prevTime - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [timeLeft, currentRound]);


  // ---------------- Functions ------------------//

  const selectOption = (option) => {
    if (!option){
      setTimeout(() => {
        setCurrentRound(0); 
      }, 1000); 
      return;
    };

    //Check if is final round
    if (currentRound >= characters.length){
      setGameOver(true);
      return;
    }

    setTimeout(()=>{
      setCurrentRound(currentRound + 1);
    }, 1000)

  };

  //Call python function to create text resume from right anime
  const helpByIA = () => {

  };

  return (
    <div className="flex flex-col items-center justify-between space-y-4 h-screen">
      <HeaderPage username={userData.username} image={props.image} />

      <SubmitButton onClick={() => {helpByIA}}>
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
              <SelectType
                key={index}
                onClick={() => selectOption(pair.r)} 
              >
                {pair.l.name}
              </SelectType>
            ))
          ) : (
            <p>Congratulations! You completed all rounds!</p>
          )}
        </div>
      </div>

      <Base round={`Round: ${currentRound+1}`} time={timeLeft} />
      {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default RoundByCharacter;
