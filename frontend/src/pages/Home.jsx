import { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderPage from "../components/HeaderPage";
import SelectType from "../components/SelectType";
import CustomSelect from "../components/CustomSelect";
import Text from "../components/Text";
import SubmitButton from "../components/SubmitButton";

function Home(props) {
  /// ------------------------- Nav ------------------------ ///
  const navigate = useNavigate();
  const redirectPages = (page) => {
    navigate(`/${page}`);
  };

  /// ------------------------- Select ------------------------ ///

  const [selectOptions] = useState([4, 8, 16, 32]);
  const [setSelectedOption] = useState(selectOptions[0]);

  const onSelect = (option) => {
    setSelectedOption(option);
    console.log("Selected option:", option);
  };

  return (
    <div className=" bg-slate-50 flex flex-col items-center h-screen w-screen space-y-8">
      <HeaderPage username={props.username} image={props.image} />

      <div className="flex flex-row gap-5 justify-between">
        <SelectType onClick={() => redirectPages("byCharacter")}>
          Guess anime by character
        </SelectType>

        <SelectType onClick={() => redirectPages("byOpening")}>
          Guess anime by opening
        </SelectType>
      </div>

      <div>
        <Text>Rounds</Text>
      </div>

      <div className="flex flex-col">
        <CustomSelect options={selectOptions} onSelect={onSelect} />
      </div>

      <SubmitButton>Start</SubmitButton>
    </div>
  );
}

export default Home;
