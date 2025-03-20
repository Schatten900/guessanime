import Text from "./Text";
import SubmitButton from './SubmitButton'

function AIPopUP(props){
    return (
        <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white rounded-lg p-8 z-30 flex flex-col items-center">
            <div>
                <Text>
                    {props.summarizedText}
                </Text>
            </div>
            <div>
                <SubmitButton onClick={props.closePopUp}> Ok </SubmitButton>
            </div>
        </div>
    )
}

export default AIPopUP;