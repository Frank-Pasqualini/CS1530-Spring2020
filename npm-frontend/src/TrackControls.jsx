import React, { Component } from 'react';
import styled from 'styled-components';
import {PlayCircle} from '@styled-icons/boxicons-regular/PlayCircle';
import {PauseCircle} from '@styled-icons/boxicons-regular/PauseCircle';
import {SkipForward} from '@styled-icons/remix-fill/SkipForward';
import {SkipBack} from '@styled-icons/remix-fill/SkipBack';

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
  height: 25px;
  color: white;
  margin: 5px;
`;

const Back = styled(SkipBack)`
  height: 25px;
  color: white;
  margin: 5px;
`;

const ForwardButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 40px;
  outline: none;
`;

const BackButton = styled.button`
  cursor: pointer;
  background-color: #444444;
  border: #444444;
  width: 40px;
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
    fetch('https://api.spotify.com/v1/me/player/play?device_id=beae69b8ce63eb244aa37e735390a02cb1d96d7c', {
      method: 'PUT',
			headers: {
			  'Authorization': `Bearer ${this.props.accessToken}`
      },
      query: {
        'client_id': `${this.props.accessToken}`
      }
		})
  }

  pauseMusic = () => {
    this.setState({ paused: true });
    fetch('https://api.spotify.com/v1/me/player/pause?device_id=beae69b8ce63eb244aa37e735390a02cb1d96d7c', {
      method: 'PUT',
			headers: {
			  'Authorization': `Bearer ${this.props.accessToken}`
      },
      query: {
        'client_id': `${this.props.accessToken}`
      }
		})
  }

  skipForward = () => {
    fetch('https://api.spotify.com/v1/me/player/next?device_id=beae69b8ce63eb244aa37e735390a02cb1d96d7c', {
      method: 'POST',
			headers: {
			  'Authorization': `Bearer ${this.props.accessToken}`
      },
      query: {
        'client_id': `${this.props.accessToken}`
      }
    })
    this.setState({ paused: false });
  }

  skipBack = () => {
    fetch('https://api.spotify.com/v1/me/player/previous?device_id=beae69b8ce63eb244aa37e735390a02cb1d96d7c', {
      method: 'POST',
			headers: {
			  'Authorization': `Bearer ${this.props.accessToken}`
      },
      query: {
        'client_id': `${this.props.accessToken}`
      }
    })
    this.setState({ paused: false });
  }

  render() {
    return (
      <ControlsContainer>
        <CurrSongInfoContainer>
          <AlbumArt src={this.props.song.albumImages[1]}/>
          <div>
            <SongTitle>{this.props.song.name}</SongTitle>
            <ArtistName>{this.props.song.artistNames[0]}</ArtistName>
          </div>
        </CurrSongInfoContainer>
        <ButtonsContainer>
          <BackButton onClick={this.skipBack}><Back /></BackButton>
          {
            // this displays the play button if the music paused and the pause button if music is playing
            this.state.paused ? <PlayButton onClick={this.playMusic}><Play /></PlayButton> : <PauseButton onClick={this.pauseMusic}><Pause /></PauseButton>
          }
          <ForwardButton onClick={this.skipForward}><Forward /></ForwardButton>
        </ButtonsContainer>
      </ControlsContainer>
    )
  }
}
  
  export default TrackControls; 