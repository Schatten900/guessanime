import Text from "./Text";
import SubmitButton from "./SubmitButton";

function Tutorial (props) {

    return (
        <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white rounded-lg p-8 z-30 flex flex-col items-center">
            <div>
                <Text>Welcome to Anime Guess</Text>
            </div>

            <div className="w-[50%] h-[40%] flex flex-col items-center">
                <div className="w-[60%] h-[40%] rounded-lg">
                    <img src={props.tutorialCurrent?.image}  alt="site-interface" />
                </div>
        
                <div className="text-nowrap text-ellipsis font-semibold text-center">
                    <p>{props.tutorialCurrent?.description}</p>
                </div>
            </div>

            <div className="flex flex-row space-x-4">
                <SubmitButton onClick={props.nextInterfaceTutorial}>Next</SubmitButton>
                <SubmitButton onClick={props.closeTutorial}>Close</SubmitButton>
            </div>
        </div>
    )
}

export default Tutorial;