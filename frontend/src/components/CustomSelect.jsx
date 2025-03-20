import { useState } from "react";

function CustomSelect(props){

    const [isOpen,setIsOpen] = useState(false);

    const optionClick = (option) => {
        props.onSelect(option);
        setIsOpen(false);
    }

    return (
        <div className="relative w-64 z-20">
            <div className="bg-white w-[250px] h-[50px] 
            border-2 border-black-400 cursor-pointer
             text-black-700 text-2xl text-center
              flex justify-center items-center p-2"
            onClick={()=>setIsOpen(!isOpen)}
            >
                <span>{props.selectedOption}</span>
            </div>
            {   
                isOpen && (
                    <ul 
                    className="rounded-md space-y-2 w-[250px] border-2 border-black-400 ">
                        {props.options.map((option,index)=>(
                            <li
                            key={index}
                            className="
                            font-semibold text-black-700 text-xl 
                            flex justify-center cursor-pointer rounded-md
                            transition duration-200 hover:bg-slate-300
                            "
                            onClick={()=>optionClick(option)}
                            >
                                {option}
                            </li>
                        ))}
                    </ul>
                )
            }
        </div>
    )
}

export default CustomSelect;