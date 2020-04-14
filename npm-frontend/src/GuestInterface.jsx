import React, { Component } from 'react';
import {Link} from "react-router-dom";
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

const LogOutButton = styled.button`
  font-size: 20px;
  border-color: #ebebeb;
  background-color: #2e83dc;
  cursor: pointer;
  border-radius: 11px;
  height: 41px;
  color: white;
  align-self: center;
  margin: 10px;
  padding: 0 10px;
  outline: none;
  font-weight: 500;
  box-shadow: 5px 6px 6px grey;
  border-color: #2e83dc;
  position: absolute;
  right: 2px;
  top: -1px;
`;

const GuestName = styled.div`
  font-size: 24px;
  align-self: center;
  margin: 0 100px;
  position: absolute;
  right: 25px;
  top: 14px;
  text-decoration: none;
`;

class GuestInterface extends Component {
  render() {
    return (
      <Container>
        <GuestName>{this.props.name}</GuestName>
        <Link to="/" onClick={this.props.logout} >
          <LogOutButton>Log Out</LogOutButton>
        </Link>
        <Header>Event {this.props.currEventCode}</Header>
        <div>
          <SearchBar/><SearchButton>Search</SearchButton>
        </div>
        <NextSong>Currently Playing</NextSong>
        <Song 
          title={this.props.songData.nowPlaying.name} 
          album={this.props.songData.nowPlaying.albumImages[1]} 
          artist={this.props.songData.nowPlaying.artistNames[0]} 
          voteCount={this.props.songData.nowPlaying.score} 
          updateSongs={this.props.updateSongs} 
          currEventCode={this.props.currEventCode} 
          userID={this.props.userID} 
          host
        />
        <NextSong>Up Next</NextSong>
        <Song 
          title={this.props.songData.upNext.name} 
          album={this.props.songData.upNext.albumImages[1]} 
          artist={this.props.songData.upNext.artistNames[0]} 
          voteCount={this.props.songData.upNext.score} 
          updateSongs={this.props.updateSongs} 
          currEventCode={this.props.currEventCode} 
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