import React,{useState, useContext} from 'react'
import AuthContext from '../context/AuthContext'

const Comments = ({ events, comments }) => {

   
    const [inputValue, setInputValue] = useState("");
    const [state, setState] = useState("");
    const [buttonClicked, setButtonClicked] = useState(false);
    
    let {authTokens} = useContext(AuthContext)
    
    const handleOnClick = async () => {
    
        let response = await fetch('http://localhost:8000/api/events/comment/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify(
                {
                    'body': inputValue,
                    'parent': events.id
                }
            ),
        })
        setState(state);
    }
        return(<>
            <h1 style = {{marginTop:'40px'}}>Comments</h1>
           { buttonClicked && <span> 
                {inputValue}
            </span>}
            <div style= {{marginTop:'10px'}} className="comment__new">
                <input style={{width: '700px', marginTop:'20px'}} value={inputValue} className="comment__new-input" type="text" placeholder="Your comment" onChange={ev => setInputValue(ev.target.value)} />
                <button style={{display: 'block', marginLeft:'10px'}} className="btn btn-dark" onClick={ handleOnClick }> 
                 Add Comment 
            </button>

            </div>
        </>)
    
}
 export default Comments;