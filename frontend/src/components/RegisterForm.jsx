import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "./Input";
import SubmitButton from "./SubmitButton";
import Title from "./Title";
import { CircleUser } from "lucide-react";
import Alert from "./Alert";
import useAlert from "../utils/alertShow";
import Link from "./Link";
import anonimo from "../assets/anonimo.png"


/// ----------------------------- Data -----------------------------///

function RegisterForm() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirm: "",
    points: 0,
    image: null
  });

  /// ---------------------------- Nav ---------------------------////

  const navigate = useNavigate(); 

  const redirectLogin = () => {
    navigate("/login");
  };

  /// --------------------------- Alert -------------------------////

  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();

  /// ----------------------------- Functions ------------------------///


  const readInputData = (e) => {
    const { name, value } = e.target; //cria uma chave-valor para o evento clicaod
    setFormData((oldData) => ({
      ...oldData, //Repassa o valor antigo do useState
      [name]: value, //Faz a chave-valor receber o dados e atualiza o state
    }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    console.log("Arquivo selecionado:", file);
    setFormData((oldData) => ({
      ...oldData,
      image: file, // Armazena o arquivo de imagem selecionado
    }));
  };


  const submitFormData = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirm) {
      setErrorMessage("Passwords must be equal");
      openAlert();
      return;
    }

    const formDataToSend = new FormData();
    formDataToSend.append("username", formData.username);
    formDataToSend.append("email", formData.email);
    formDataToSend.append("password", formData.password);

    if (formData.image) {
      formDataToSend.append("image", formData.image);
    } 
    else {
      const image = await fetch(anonimo);
      const blob = await image.blob();
      const file = new File([blob], "anonimo.png", { type: "image/png" });
      formDataToSend.append("image", file);
    }

    try {
      const response = await fetch("http://localhost:5050/register", {
        method: "POST",
        body: formDataToSend,
      });

      const data = await response.json(); 
      console.log("Resposta do backend:", data); 

      if (!response.ok) {
        setErrorMessage(data.message || "Error occurred during registration");
        openAlert();
        return;
    }

<<<<<<< HEAD
      navigate("/", { state: { userData: data }});
=======
      navigate("/", { state: { data: data }});
>>>>>>> 72179ae2e09fad21eb6c049313959d099f3e7b97
    
    } 
    catch (error) {
      setErrorMessage(error.message || "Error occurred during registration");
      openAlert();
    }
  };

  return (
<<<<<<< HEAD
    <form 
    onSubmit={submitFormData}
    className="w-[500px] h-[500px] rounded-xl shadow-sm bg-slate-100 flex flex-col space-y-5 items-center justify-center p-3">
=======
    <div className="w-[500px] h-[500px] rounded-xl shadow-sm bg-slate-100 flex flex-col space-y-5 items-center justify-center p-3">
>>>>>>> 72179ae2e09fad21eb6c049313959d099f3e7b97
      <input 
      type="file"
      name="image"
      onChange={handleImageChange}
      style={{ display: "none" }} 
      id="image-upload" 
      />
      <label htmlFor="image-upload"> 
        <CircleUser size={60} color="gray" style={{ cursor: "pointer" }} />
      </label>
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
<<<<<<< HEAD
    </form>
=======
    </div>
>>>>>>> 72179ae2e09fad21eb6c049313959d099f3e7b97
  );
}

export default RegisterForm;
