function SelectType(props){

    return (
        <div className="h-[60px] w-[250px] rounded-xl shadow-md
         bg-blue-700 transition duration-300 hover:scale-110 
         flex items-center justify-center
          text-white font-serif text-xl cursor-pointer"
          onClick={props.onClick}
          >
            {props.children}
        </div>
    )

}

export default SelectType;