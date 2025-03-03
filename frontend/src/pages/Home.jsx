import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import HeaderPage from "../components/HeaderPage";
import SelectType from "../components/SelectType";
import CustomSelect from "../components/CustomSelect";
import Text from "../components/Text";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";

function Home(props) {
  /// ------------------------- user ----------------------- ///
  const location = useLocation();
  const userData = location.state?.data || [];

  /// ------------------------- Nav ------------------------ ///
  const navigate = useNavigate();

  /// ------------------------- Select ------------------------ ///

  const [selectOptions] = useState([4, 8, 16, 32]);
  const [setSelectedOption] = useState(selectOptions[0]);

  const [characterList, setCharacterList] = useState([]);

  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();

  const onSelect = (option) => {
    setSelectedOption(option);
    console.log("Selected option:", option);
  };

  const requestChars = async () => {
    let rounds = setSelectedOption;
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

      const data = await response.json(); // Dando erro aqui
      setCharacterList(data);

      //Redirect to page with character list
      navigate("byCharacter", { state: { characters: data }, user: {userData:userData} , rounds : {rounds} });
    } catch (error) {
      setErrorMessage(error.message);
      openAlert();
      
    }
  };

  return (
    <div className=" bg-slate-50 flex flex-col items-center h-screen w-screen space-y-8">
      <HeaderPage username={props.username} image={props.image} />

      <div className="flex flex-row gap-5 justify-between">
        <SelectType onClick={() => requestChars()}>
          Guess anime by character
        </SelectType>
      </div>

      <div>
        <Text>Rounds</Text>
      </div>

      <div className="flex flex-col">
        <CustomSelect options={selectOptions} onSelect={onSelect} />
      </div>
      {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default Home;
