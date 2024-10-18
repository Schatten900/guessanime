import {useState} from 'react';

//Function which permit interaction with custom alert
const useAlert = () => {
    const [showAlert,setAlert] = useState(false);
    const openAlert = () => setAlert(true);
    const closeAlert = () => setAlert(false);
    return {
        showAlert,
        openAlert,
        closeAlert
    };
};
export default useAlert;

