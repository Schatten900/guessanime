import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import HeaderPage from "../components/HeaderPage";
import SelectType from "../components/SelectType";
import CustomSelect from "../components/CustomSelect";
import Text from "../components/Text";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import Tutorial from "../components/Tutorial";
import "../styles/images.css";
import gojo from "../assets/gojo4.jpg";
import naruto from "../assets/naruto.jpg";
import luffy from "../assets/luffy.jpg";
//import interface1 from '../assets/interface1.jpg';
//import interface2 from '../assets/interface2.jpg';
//import interface3 from '../assets/interface3.jpg';

function Home() {
  /// ------------------------- user ----------------------- ///
  const [userData, setUserData] = useState(JSON.parse(sessionStorage.getItem("user")));

  useEffect(() => {
    const storedUser = JSON.parse(sessionStorage.getItem("user"));
    if (storedUser) {
      setUserData(storedUser);
    }
  }, [sessionStorage.getItem("user")]);

  /// ------------------------- Nav ------------------------ ///
  const navigate = useNavigate();

  /// ------------------------- State ------------------------ ///

  const [selectOptions] = useState([4, 8, 16, 32]);
  const [selectedOption, setSelectedOption] = useState(selectOptions[0]);

  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();
  const [tutorial, setTutorial] = useState(true);

  const [tutorialText] = useState([
    {
      id:0,
      image: naruto,
      description:
        "In every round will be give four characters options and one correct character image to user answer the right name character.",
    },
    {
      id:1,
      image: luffy,
      description:
        "While rounds is running, is possible to ask for help from AI to make a quick anime resume to reduce possibilities.",
    },
    {
      id:2,
      image: gojo,
      description:
        "In ranking page you can see the highest points top 20 position players",
    },
  ]);

  const [tutorialCurrent, setTutorialCurrent] = useState(tutorialText[0]);

  /// --------------------- Functions -------------------///

  const closeTutorial = () => {
    setTutorial(false);
  };

const nextInterfaceTutorial = () => {
  const nextIndex = tutorialCurrent?.id + 1;
  if (nextIndex < tutorialText.length) {
    setTutorialCurrent(tutorialText[nextIndex]);
  } 
  else {
    setTutorial(false); 
  }
};

  const onSelect = (option) => {
    setSelectedOption(option);
    console.log("Selected option:", option);
  };

  const requestChars = async (rounds) => {
    //Send rounds quantity to backend
    try {
      const response = await fetch("http://localhost:5050/byCharacter", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          message: "byCharacter",
          quantity: rounds,
        }),
      });

      if (!response.ok) {
        const data = await response.text();
        setErrorMessage(data.message || "Ocurred some error on alimentation");
        openAlert();
        return;
      }

      const data = await response.json();

      //Redirect to page with character list
      navigate("byCharacter", {
        state: { characters: data, userData: userData, rounds: rounds },
      });
    } catch (error) {
      setErrorMessage(error.message);
      openAlert();
    }
  };

  return (
    <div className="bg-white flex flex-col items-center h-screen w-screen space-y-8">
      <HeaderPage userData={userData} setUserData={setUserData} />

      {!tutorial ? (
        <div className="flex flex-row gap-5 justify-between">
          <SelectType onClick={() => requestChars(selectedOption)}>
            Guess characters
          </SelectType>
        </div>
      ) : (
        <Tutorial 
        closeTutorial={closeTutorial} 
        tutorialCurrent={tutorialCurrent} 
        nextInterfaceTutorial={nextInterfaceTutorial} />
      )}

      <div>
        <Text>Rounds</Text>
      </div>

      <div className="flex flex-col">
        <CustomSelect
          options={selectOptions}
          onSelect={onSelect}
          selectedOption={selectedOption}
        />
      </div>

      <img
        src={gojo}
        alt="Personagem1"
        className="home-character-right-image"
      />
      <img
        src={luffy}
        alt="Personagem2"
        className="home-character-center-image"
      />
      <img
        src={naruto}
        alt="Personagem3"
        className="home-character-left-image"
      />

      {tutorial && (
        <div className="absolute inset-0 bg-black bg-opacity-50 z-20"></div>
      )}

      {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default Home;
