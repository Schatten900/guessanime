import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import Title from "./Title";
import { CircleUser } from "lucide-react";
import Alert from "./Alert";
import useAlert from "../utils/alertShow";
import Link from "./Link";

function RegisterForm() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirm: "",
  });

  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();

  const navigate = useNavigate(); //Hook para navegar entre as paginas

  const readInputData = (e) => {
    const { name, value } = e.target; //cria uma chave-valor para o evento clicaod
    setFormData((oldData) => ({
      ...oldData, //Repassa o valor antigo do useState
      [name]: value, //Faz a chave-valor receber o dados e atualiza o state
    }));
  };

  const redirectLogin = () => {
    navigate("/login");
  };

  const submitFormData = async (e) => {
    console.log(formData);

    e.preventDefault();

    if (formData.password !== formData.confirm) {
      setErrorMessage("Passwords must be equal");
      openAlert();
      return;
    }

    try {
      const response = await fetch("http://localhost:5050/register", {
        //Url Dinamica
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const data = await response.text(); //Valor retornado pelo backEnd
        setErrorMessage(data.message || "Error occurred during registration");
        openAlert(); // Abre o alerta
        console.log(data.message || "Ocorreu um erro");
      } else {
        navigate("/");
      }
    } catch (error) {
      setErrorMessage(error || "Error occurred during registration");
      console.log("Erro ao enviar os dados", error);
      openAlert();
    }
  };

  return (
    <div className="w-[500px] h-[500px] rounded-xl shadow-sm bg-slate-100 flex flex-col space-y-5 items-center justify-center p-3">
      <CircleUser size={60} color="gray" />
      <Title>Register new account</Title>
      <Input
        type="text"
        placeholder="Username"
        name="username"
        value={formData.username}
        onChange={readInputData}
      />
      <Input
        type="text"
        placeholder="Email"
        name="email"
        value={formData.email}
        onChange={readInputData}
      />
      <Input
        type="password"
        placeholder="Password"
        name="password"
        value={formData.password}
        onChange={readInputData}
      />
      <Input
        type="password"
        placeholder="Confirm Password"
        name="confirm"
        value={formData.confirm}
        onChange={readInputData}
      />
      <SubmitButton onClick={submitFormData}>Registrar</SubmitButton>
      <Link onClick={redirectLogin}>You do not have an account yet?</Link>

      {showAlert && <Alert message={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default RegisterForm;
