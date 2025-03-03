import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import Title from "./Title";
import Link from "./Link";
import { CircleUser } from "lucide-react";
import Alert from "./Alert";
import useAlert from "../utils/alertShow";

function LoginForm() {
  // ------------------------  Input ------------------------ //

  const [formData, setForm] = useState({
    email: "",
    password: "",
  });
  const readInputData = (e) => {
    const { name, value } = e.target;
    setForm((oldData) => ({
      ...oldData,
      [name]: value,
    }));
  };

  // ------------------------  Error ------------------------ //

  const { showAlert, openAlert, closeAlert } = useAlert();
  const [alertMessage, setAlertMessage] = useState("");


  // ------------------------  Nav ------------------------ //
  const navigate = useNavigate();

  const redirectRegister = () => {
    navigate("/register");
  };
  // ------------------------  Functions ------------------------ //

  const submiteFormData = async (e) => {
    e.preventDefault();


    try {
      const response = await fetch("http://localhost:5050/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const data = await response.json(); 

      if (!response.ok) {
        setAlertMessage(data.message || "Error occurred during registration");
        openAlert();
        return;
    }
      navigate("/", {state: {data: data}});
      
    } catch (error) {
      setAlertMessage(error || "Something went wrong while logging");
      openAlert();
    }
  };

  return (
    <div className="w-[450px] h-[450px] rounded-xl shadow-md bg-slate-100 flex flex-col items-center justify-evenly p-3">
      <CircleUser size={60} color="gray" />
      <Title>Sign in</Title>
      <div className="flex flex-col items-center w-[110%] space-y-6">
        <Input
          placeholder="Email"
          type="text"
          name="email"
          value={formData.email}
          onChange={readInputData}
        />
        <Input
          placeholder="Password"
          type="password"
          name="password"
          value={formData.password}
          onChange={readInputData}
        />
        <SubmitButton onClick={submiteFormData}>Login</SubmitButton>
      </div>
      <Link onClick={redirectRegister}>You do not have an account yet?</Link>

      {showAlert && <Alert message={alertMessage} onClick={closeAlert} />}
    </div>
  );
}

export default LoginForm;
