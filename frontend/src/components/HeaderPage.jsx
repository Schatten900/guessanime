import { House } from "lucide-react";
import { Medal } from "lucide-react";
import { LogOut } from "lucide-react";
import { Pencil } from "lucide-react";
import { useNavigate } from "react-router-dom";
import Alert from "../components/Alert";
import useAlert from "../utils/alertShow";
import { useState } from "react";
import { useRef } from "react";

function HeaderPage(props) {
  // ---------------------------------- Nav ---------------------------------//
  const navigate = useNavigate();
  const redirectPages = (page) => {
    navigate(`/${page}`, { state: { userData: props.userData } });
  };

  //----------------------------------- State ----------------------------//

  const [messageError, setErrorMessage] = useState("");
  const { showAlert, openAlert, closeAlert } = useAlert();
  const fileInputRef = useRef(null);

  // ---------------------------------- Functions ---------------------------//

  const logoutUser = () => {
    sessionStorage.removeItem("user");
    navigate("/login");
  };

  const changePicture = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formDataToSend = new FormData();
    formDataToSend.append("image", file);
    formDataToSend.append("id", props.userData.id);

    try {
      const response = await fetch(
        "http://localhost:5050/user/changingPicture",
        {
          method: "POST",
          body: formDataToSend,
        }
      );

      if (!response.ok) {
        const error = await response.json();
        setErrorMessage(error.message);
        openAlert();
        return;
      }

      const data = await response.json();
      props.setUserData(data.user);
      sessionStorage.setItem("user", JSON.stringify(data.user));

    } catch (error) {
      setErrorMessage(error.message);
      openAlert();
    }
  };
  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  // ------------------------------- Effects -------------------//

  return (
    <div className="bg-blue-700 h-[15vh] w-[100%] flex flex-row justify-between items-center p-8">
      <div className="flex flex-row gap-2 items-center transform hover:scale-105 relative">
        <div className="relative cursor-pointer" onClick={handleImageClick}>
          <img
            className="rounded-full w-[80px] h-[80px] object-cover border-2 border-white"
            src={`data:image/jpg;base64,${props.userData?.image}`}
            alt="user-image"
          />

          <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 opacity-0 hover:opacity-100 transition-opacity rounded-full">
            <Pencil size={24} color="white" />
          </div>
        </div>

        <input
          type="file"
          accept="image/*"
          ref={fileInputRef}
          onChange={changePicture}
          className="hidden"
        />
        <div>
          <h1 className="text-white font-extrabold">
            {String(props.userData?.username || "Guest")}
          </h1>
          <h1 className="text-white font-extrabold">
            Points: {typeof props.userData?.points === "object" ? JSON.stringify(props.userData?.points) : props.userData?.points}
          </h1>
        </div>
      </div>

      <div className="flex flex-row space-x-6">
        <div
          className="flex flex-col gap-1 items-center
                    transform hover:scale-105"
        >
          <Medal
            onClick={() => redirectPages("ranking")}
            size={50}
            color="white"
            cursor="pointer"
          />
          <h1 className="text-white font-extrabold">Ranking</h1>
        </div>

        <div
          className="flex flex-col gap-1 items-center
                transform hover:scale-105"
        >
          <House
            onClick={() => redirectPages("")}
            size={50}
            color="white"
            cursor="pointer"
          />
          <h1 className="text-white font-extrabold">Home</h1>
        </div>

        <div
          className="flex flex-col gap-1 items-left
                transform hover:scale-105"
        >
          <LogOut
            onClick={() => logoutUser()}
            size={50}
            color="white"
            cursor="pointer"
          />
          <h1 className="text-white font-extrabold">Exit</h1>
        </div>
      </div>
      {showAlert && <Alert messageError={messageError} onClick={closeAlert} />}
    </div>
  );
}

export default HeaderPage;
