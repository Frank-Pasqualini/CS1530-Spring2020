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
  constructor(){
    super();

    this.state = {

    }
  }
  render() {
    return (
      <Container>
        <Header>Event 2011</Header>
        <div>
          <SearchBar/><SearchButton>Search</SearchButton>
        </div>
        <NextSong>Up Next</NextSong>
        <Song title='song1' album='album1' artist='artist1' voteCount='19'/>
        <Song title='song2' album='album2' artist='artist2' voteCount='14' />
      </Container>
    )
  }
}
  
  export default GuestInterface; 