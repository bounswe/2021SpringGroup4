import './CommentList.css';
import React, { useState, useContext } from 'react';
import { Link, NavLink, useHistory } from 'react-router-dom';
import AuthContext from '../context/AuthContext';



const EventList = ({events: eventsFromParent}) => {
        let {authTokens, logoutUser} = useContext(AuthContext);
        const [inputValue, setInputValue] = useState("");
        const [state, setState] = useState("");
        const [buttonClicked, setButtonClicked] = useState(false);
        const history = useHistory()
        const [selectedUser, setSelectedUser] = useState(null)
        const [events, setEvents] = useState(eventsFromParent)
        console.log(events)
        const onFindUser = ()=> {
            if(!!selectedUser){
                const params = new URLSearchParams()
                params.append('userName', selectedUser)
                history.push('/userProfileSelect?' + params)
            }
        }
        const handleOnClick = async () => {
    
            fetch('http://3.67.188.187:8000/api/events/comment/', {
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
            }).then(()=>{
                fetch('http://3.67.188.187:8000/api/events/' + events.id + '/', {
                    method: 'GET'
                }).then(res => {
                    console.log(res);
                    if(!res.ok){
                        console.log(res);
                        throw Error('could not fetch the data for that resource');
                    }
                    console.log(res);
                    return res.json();
                })
                .then(data => {
                    console.log(data)
                    setEvents(data);
                })
            })
        }

        return (  
            <div>
                
                <h1 style = {{marginTop:'40px'}}>Comments</h1>
                    { buttonClicked && <span> 
                    {inputValue}
                    </span>}
                <center><div style= {{marginTop:'10px'}} className="comment__new">
                    <input style={{width: '700px', marginTop:'20px'}} value={inputValue} className="comment__new-input" type="text" placeholder="Your comment" onChange={ev => setInputValue(ev.target.value)} />
                    <button style={{display: 'block', marginLeft:'10px'}} className="btn btn-dark" onClick={ handleOnClick }> 
                    Add Comment 
                    </button>
                </div></center>
                <div className="event-list-comment">
                    <div className = "event-grid-comment">{events.body.comments.map((comment) => (
                        <div className="event-preview-comment" key={comment.id}>
                            <h2 style={{textAlign:'center', marginLeft:'0px'}}>{ comment.body }</h2>
                            <p style={{display:"block", cursor:"pointer", textAlign:'center', marginLeft:'0px'}} onClick={() => {
                                setSelectedUser(comment.owner);
                                onFindUser();
                            }}> {comment.owner.toUpperCase()}</p>
                        </div>
                    ))}
                    </div>
                </div>
            </div>
            
    
        );
    }
    export default EventList;


  