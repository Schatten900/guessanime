import Text from "./Text";

function Base(props){

    return (
        <div 
        className="w-[100%] h-[50px] bg-blue-700 flex flex-row justify-between p-2"
        >
            <Text color="white">{props.round}</Text>
            <Text color="white">{props.time}</Text>

        </div>
    )
}

export default Base;