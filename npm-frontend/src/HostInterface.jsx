import React, { Component } from 'react';
import styled from 'styled-components';
import TrackControls from './TrackControls';
import Song from './Song';

const SongContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 0 200px 0;
`;

const PageContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const Header = styled.div`
  color: #328bea;
  font-size: 70px;
  margin: 20px 0;
`;

const NextSong = styled.div`
  font-size: 50px;
  color: white;
  align-self: end;
  margin: 30px 0 0;
`;

const SongsLoading = styled.div`
  color: white;
  font-size: 60px;
  margin: 43px 0;
  display: flex;
  justify-content: center;
`;

class HostInterface extends Component {
  constructor() {
    super()

    this.state = {
      loading: true
    }
  }

  componentDidMount() {
    fetch(`http://localhost:8080/api/event?eventCode=${this.props.currEventCode}`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.props.updateSongs(data);
      this.setState({ loading: false });
    });
  }

  removeSong = (id) => {
    fetch(`http://localhost:8080/api/remove_track?eventCode=${this.props.currEventCode}&hostId=chipmilotis&trackId=${id}&accessToken=${this.props.accessToken}`);

    fetch(`http://localhost:8080/api/event?eventCode=${this.props.currEventCode}`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.props.updateSongs(data);
      this.setState({ loading: false });
    });
  }

  render() {
    return (
      <PageContainer>
      <SongContainer>
        <Header>Event {this.props.currEventCode}</Header>
        <NextSong>Up Next</NextSong>
        {this.state.loading ? <div></div> : <Song title={this.props.songData.upNext.name} album={this.props.songData.upNext.albumImages[1]} artist={this.props.songData.upNext.artistNames[0]} voteCount={this.props.songData.upNext.score} host />}
        <NextSong>In The Queue</NextSong>
        {
          this.state.loading ? <SongsLoading>Loading Songs...</SongsLoading> :
          // This renders all the song entries we created
          // Create a song entry for every song in the list
          this.props.songData.queue.trackList.map((item, key) =>
            <Song title={item.name} album={item.albumImages[1]} artist={item.artistNames[0]} voteCount={item.score} id={item.id} removable removeSong={this.removeSong} host key={key}/>
          )
        }
      </SongContainer>
      { this.state.loading ? <div></div> : 
        <TrackControls accessToken={this.props.accessToken} song={this.props.songData.nowPlaying} />
      }
    </PageContainer>
    )
  }
}
  
  export default HostInterface; 