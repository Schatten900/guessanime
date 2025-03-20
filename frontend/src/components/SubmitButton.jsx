<<<<<<< HEAD
function SubmitButton(props) {
  return (
    <button
      type={props.type || "button"}
      className="w-[200px] h-[40px] rounded-lg bg-black text-white font-bold transition duration-300 hover:scale-105"
      onClick={props.onClick}
    >
      {props.children}
    </button>
  );
}

export default SubmitButton;
=======
function SubmitButton(props){
    return (
        <button
        className="w-[200px] h-[40px] rounded-lg bg-black text-white font-bold transition duration-300 hover:scale-105"
        onClick={props.onClick}
        >
            {props.children}
        </button>
    )
}

export default SubmitButton;
>>>>>>> 72179ae2e09fad21eb6c049313959d099f3e7b97
