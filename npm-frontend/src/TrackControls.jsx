import React, { Component } from 'react';
import styled from 'styled-components';
import {PlayCircle} from '@styled-icons/boxicons-regular/PlayCircle';
import {PauseCircle} from '@styled-icons/boxicons-regular/PauseCircle';
import {SkipForward} from '@styled-icons/remix-fill/SkipForward';
import {SkipBack} from '@styled-icons/remix-fill/SkipBack';
import DefaultArt from './defaultart.png';

const ControlsContainer = styled.div`
  height: 100px;
  position: fixed;
  bottom: 0%;
  width: 100%;
  background-color: #444444;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ButtonsContainer = styled.div`
  display: flex;
  align-items: center;
`;

const CurrSongInfoContainer = styled.div`
  position: absolute;
  left: 20px;
  display: flex;
  align-items: center;
`;

const AlbumArt = styled.img`
  height: 75px;
  margin: 15px;
`;

const SongTitle = styled.div`
  color: white;
  font-size: 25px;
`;

const ArtistName = styled.div`
  color: #aaa;
  font-size: 18px;
`;

const Play = styled(PlayCircle)`
  height: 40px;
  color: white;
  margin: 5px;
`;

const Pause = styled(PauseCircle)`
  height: 40px;
  color: white;
  margin: 5px;
`;

const Forward = styled(SkipForward)`
  height: 35px;
  color: white;
  margin: 5px;
`;

const Back = styled(SkipBack)`
  height: 35px;
  color: white;
  margin: 5px;
`;

const ForwardButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 50px;
  outline: none;
`;

const BackButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 50px;
  outline: none;
`;

const PlayButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 50px;
  outline: none;
`;

const PauseButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 50px;
  outline: none;
`;



class TrackControls extends Component {
  constructor() {
    super();
    this.state = {
      paused: true
    }
  }

  playMusic = () => {
    this.setState({ paused: false });
  }

  pauseMusic = () => {
    this.setState({ paused: true });
  }

  render() {
    return (
      <ControlsContainer>
        <CurrSongInfoContainer>
          <AlbumArt src={DefaultArt}/>
          <div>
            <SongTitle>Song Name</SongTitle>
            <ArtistName>Artist Name</ArtistName>
          </div>
        </CurrSongInfoContainer>
        <ButtonsContainer>
          <BackButton><Back /></BackButton>
          {
            // this displays the play button if the music paused and the pause button if music is playing
            this.state.paused ? <PlayButton onClick={this.playMusic}><Play /></PlayButton> : <PauseButton onClick={this.pauseMusic}><Pause /></PauseButton>
          }
          <ForwardButton><Forward /></ForwardButton>
        </ButtonsContainer>
      </ControlsContainer>
    )
  }
}
  
  export default TrackControls; 