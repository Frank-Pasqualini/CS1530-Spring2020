import React, { Component } from 'react';
import styled from 'styled-components';
import Song from './Song';

const SearchBar = styled.input.attrs(props => ({
  type: "text",
  placeholder: "Search for a song"
}))`
  width: 515px;
  height: 40px;
  font-size: 25px;
  border-radius: 7px;
  margin: 10px;
  padding: 7px;
  font-family: Tahoma;
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
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

const SearchButton = styled.button`
  background-color: #2A77C9;
  color: white;
  height: 60px;
  width: 140px;
  font-size: 30px;
  border-radius: 13px;
  cursor: pointer;
  margin: 15px;
  border-color: #272727;
  font-weight: bold;
`;

class GuestInterface extends Component {
  render() {
    return (
      <Container>
        <Header>Event {this.props.currEventCode}</Header>
        <div>
          <SearchBar/><SearchButton>Search</SearchButton>
        </div>
        <NextSong>Up Next</NextSong>
        <Song 
          title={this.props.songData.upNext.name} 
          album={this.props.songData.upNext.albumImages[1]} 
          artist={this.props.songData.upNext.artistNames[0]} 
          voteCount={this.props.songData.upNext.score} 
          updateSongs={this.props.updateSongs} 
          currEventCode={this.props.currEventCode} 
          userID={this.props.userID} 
          upVoteSong={this.props.upVoteSong}
          downVoteSong={this.props.downVoteSong}
          host
        />
        <NextSong>In The Queue</NextSong>
        {
          // This renders all the song entries we created
          // Create a song entry for every song in the list
          this.props.songData.queue.trackList.map((item, key) =>
            <Song 
              title={item.name} 
              album={item.albumImages[1]} 
              artist={item.artistNames[0]} 
              voteCount={item.score} 
              id={item.id} 
              key={key} 
              userID={this.props.userID} 
              updateSongs={this.props.updateSongs} 
              currEventCode={this.props.currEventCode} 
            />
          )
        }
      </Container>
    )
  }
}
  
  export default GuestInterface; 