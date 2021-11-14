import React from 'react'
import '../App.css';
import {Button} from './Button';
import './Home2background.css';
function Home2background() {
    return (
    <div className='home2background-container'>
      <video src='/images2/video-1.mp4' autoPlay loop muted />
      <div className='hero-btns'>
        <Button
          className='btns'
          buttonStyle='btn--outline'
          buttonSize='btn--large'
        >
          This is an amateur sports platform where you can find people to work out with.
        </Button>
       
      </div>
    </div>
  );
}
export default Home2background
