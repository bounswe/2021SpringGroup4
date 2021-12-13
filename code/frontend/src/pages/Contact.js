import React from 'react'

function Contact() {

    const lat = 41.084049;
    const lng = 29.051020;

    return (
        <div>
             <h2> Contact Information </h2>

                <div align="left">   
                 <p> </p>
                <h4> You could contact with us ! </h4>

                <p> Boğaziçi University </p>
                <p> 34342 Bebek/Istanbul Türkiye  </p>
                <p> Phones: +90 212 359 54 00  </p>
                <p> Fax: +90 212 265 63 57  </p>
                </div>
               

                <img src= {`https://maps.googleapis.com/maps/api/staticmap?center=${lat},${lng}&zoom=14&size=400x400&sensor=false&markers=color:red%7C${lat},${lng}&key=${process.env.REACT_APP_GOOGLE_KEY}`} alt='' />

        </div>
    )
}

export default Contact
