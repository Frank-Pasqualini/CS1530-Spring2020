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

class HostInterface extends Component {
  constructor(props) {
    super()
    // Create a song entry for every song in the list
    this.songs = props.songList.map((item, key) =>
      <Song title={item.title} album={item.img_url} artist={item.artist} voteCount={item.voteCount} key={key}/>
    );
  }
  render() {
    return (
      <PageContainer>
      <SongContainer>
        <Header>Event 2011</Header>
        <NextSong>Up Next</NextSong>
        {
          // This renders all the song entries we created
          this.songs
        }
      </SongContainer>
      <TrackControls />
    </PageContainer>
    )
  }
}
  
  export default HostInterface; 