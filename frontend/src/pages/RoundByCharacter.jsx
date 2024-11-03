import HeaderPage from "../components/HeaderPage";
import { useState } from "react";
import SelectType from "../components/SelectType";
import Base from "../components/Base";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import SubmitButton from "../components/SubmitButton";

function RoundByCharacter(props) {
  // ---------------- Options ----------------------//
  const [charOption, setCharOption] = useState("");
  const [listChars] = useState([]);

  // ---------------- Error ----------------------//
  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();

  const selectOption = () => {};

  const helpByIA = () => {};

  const requestChars = async () => {
    try {
      //Request from backend the info of round, like anime character image and options
      const response = await fetch("http://localhost/byCharacter");
    } catch (error) {
      setErrorMessage(error || "Error occurred during registration");
      console.log("Erro ao enviar os dados", error);
      openAlert();
    }
  };

  return (
    <div className="flex flex-col items-center justify-between space-y-4 h-screen">
      <HeaderPage username={props.username} image={props.image} />

      <SubmitButton
        onClick={() => {
          helpByIA;
        }}
      >
        Help
      </SubmitButton>

      <div className="flex flex-col justify-center items-center space-y-8">
        <div className="w-[500px] h-[300px] bg-white rounded-3xl border-2 border-black-200">
          <img
            className="w-[100%] h-[100%] rounded-3xl"
            src={props.image}
            alt="char-image"
          />
        </div>

        <div className="w-[600px] h[120px] flex flex-wrap justify-center gap-2 ">
          <SelectType onClick={() => selectOption()}>
            {props.option1}
          </SelectType>
          <SelectType onClick={() => selectOption()}>
            {props.option2}
          </SelectType>
          <SelectType onClick={() => selectOption()}>
            {props.option3}
          </SelectType>
          <SelectType onClick={() => selectOption()}>
            {props.option4}
          </SelectType>
        </div>
      </div>

      <Base round={`Round: ${1}`} time="60" />
    </div>
  );
}

export default RoundByCharacter;
