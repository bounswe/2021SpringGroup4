import React,{useState, useContext} from 'react'
import { Button } from './Button';
import AuthContext from '../context/AuthContext'

const Comments = ({ comments }) => {

    const [inputValue, setInputValue] = useState("");
    const [buttonClicked, setButtonClicked] = useState(false);
    
    let {authTokens} = useContext(AuthContext)
    
    const handleOnClick = async () => {
       /* 
        // Başka sayfaya da yönelendirme yapabilirsiniz
        let response = await fetch('http://3.67.188.187:8000/api/events/', {
            method:'PATCH',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify(
                {
                    'comments' : inputValue
                    // BURAYI DÜZELTİN
                }
            )
        }) */

    
setButtonClicked(true)

    }
  
        return(<>
            <h1>Comments</h1>
           { buttonClicked && <span> 
                {inputValue}
            </span>}
            <div className="comment__new">
                <input value={inputValue} className="comment__new-input" type="text" placeholder="Your comment" onChange={ev => setInputValue(ev.target.value)} />
            <Button onClick={handleOnClick }> 
              Add Comment 
            </Button>

            </div>
        </>)
    
}
 export default Comments;
