import { createContext, useState, useEffect } from 'react'
import jwt_decode from "jwt-decode";
import { useHistory } from 'react-router-dom'

const AuthContext = createContext()

export default AuthContext;


export const AuthProvider = ({children}) => {
    let [authTokens, setAuthTokens] = useState(()=> localStorage.getItem('authTokens') ? JSON.parse(localStorage.getItem('authTokens')) : null)
    let [user, setUser] = useState(()=> localStorage.getItem('authTokens') ? jwt_decode(localStorage.getItem('authTokens')) : null)
    let [loading, setLoading] = useState(true)
    let [myusername, setMyUser]= useState('')
   

    const history = useHistory()

    let loginUser = async (e )=> {
        e.preventDefault()
        setMyUser(e.target.username.value)

        let response = await fetch('http://3.67.188.187:8000/api/auth/login/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify({'username':e.target.username.value, 'password':e.target.password.value})
         
        })
        
        let data = await response.json()

        if(response.status === 200){
            
            setAuthTokens(data)
            setUser(jwt_decode(data.access))
            localStorage.setItem('authTokens', JSON.stringify(data))
            console.log(data)
            history.push('/')
        }
        else if(response.status === 401){
            alert('Your username or password is wrong')
        }
        
        else{
            console.log(response.data)
            alert('Please check your credentials')
        }
    }


    let logoutUser = () => {
        setAuthTokens(null)
        setUser(null)
        localStorage.removeItem('authTokens')
        history.push('/login')
    }


    
    let createEvent = async (e) => {
        e.preventDefault()
        let response = await fetch('http://3.67.188.187:8000/api/events/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer ' + String(authTokens.access)
            },
            body:JSON.stringify({'title':e.target.title.value, 'description':e.target.description.value, 'date':e.target.date.value , 'time': e.target.time.value , 
            'duration': e.target.duration.value , 'location': e.target.location.value ,'sportType': e.target.sportType.value, 'maxPlayers': e.target.maxPlayers.value  })
        })
        
        let data = await response.json()

        
        if(response.status === 200){
            console.log("Event Data Submitted")
            history.push('/')
        }
        
        else{
            alert('Please check your credentials')
        }
        
    }



    





    let updateToken = async ()=> {

        let response = await fetch('http://3.67.188.187:8000/api/auth/login/', {
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify({'refresh':authTokens?.refresh})
        })

        let data = await response.json()
        
        if (response.status === 200){
            setAuthTokens(data)
            setUser(jwt_decode(data.access))
            localStorage.setItem('authTokens', JSON.stringify(data))
        }else{
            logoutUser()
        }

        if(loading){
            setLoading(false)
        }
    }

    let contextData = {
        user:user,
        authTokens:authTokens,
        loginUser:loginUser,
        logoutUser:logoutUser,
        myusername:myusername,
    }


    useEffect(()=> {

        if(loading){
            updateToken()
        }

        let fourMinutes = 1000 * 60 * 60

        let interval =  setInterval(()=> {
            if(authTokens){
                updateToken()
            }
        }, fourMinutes)
        return ()=> clearInterval(interval)

    }, [authTokens, loading])

    return(
        <AuthContext.Provider value={contextData} >
            {loading ? null : children}
        </AuthContext.Provider>
    )
}