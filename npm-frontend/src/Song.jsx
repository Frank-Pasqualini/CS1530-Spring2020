import React, { Component } from 'react';
import styled from 'styled-components';
import {ArrowUp} from '@styled-icons/entypo/ArrowUp'
import {ArrowDown} from '@styled-icons/entypo/ArrowDown'


const SongBox = styled.div`
  height: 100px;
  width: 700px;
  background: #272726;
  margin: 6px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-radius: 5px;
  border: #7a7b80;
  border-style: solid;
  border-radius: 5px;
  border-width: 1px;
  color: white;
`;

const AlbumArt = styled.img`
  height: 75px;
  margin: 15px;
`;

const SongTitle = styled.div`
  font-size: 25px;
`;

const ArtistName = styled.div`
  color: #aaa;
  font-size: 18px;
`;

const SongInfo = styled.div`
  width: 450px;
`;

const Up = styled(ArrowUp)`
  height: 35px;
  color: ${props => props.up ? '#3e97d8' : 'white'};
`;
const Down = styled(ArrowDown)`
  height: 35px;
  color: ${props => props.down ? '#3e97d8' : 'white'};
`;

const ArrowButton = styled.button`
  cursor: pointer;
  background-color: #272726;
  border: #272726;
  outline: none;
`;

const RatingBox = styled.div`
  display: flex;
  align-items: center;
`;

class Song extends Component {
  constructor(props) {
    super();

    this.state = {
      up: false,
      down: false,
      votes: props.voteCount || 0
    }
  }

  upVote = () => {
    let votes = this.state.votes
    // If down was already clicked, need to change value by 2
    if(this.state.down) { votes += 2; }
    else if(!this.state.up) { votes += 1; }
    this.setState({ up: true, down: false, votes: votes })
  }

  downVote = () => {
    let votes = this.state.votes
    // If up was already clicked, need to change value by 2
    if(this.state.up) { votes -= 2; }
    else if(!this.state.down) { votes -= 1; }
    this.setState({ up: false, down: true, votes: votes })
  }

  render() {
    return (
      <SongBox>
        <AlbumArt src={this.props.album} />
        <SongInfo>
          <SongTitle>{this.props.title}</SongTitle>
          <ArtistName>{this.props.artist}</ArtistName>
        </SongInfo>
        <RatingBox>
          <ArrowButton onClick={this.upVote}><Up up={this.state.up}/></ArrowButton>
          <div>{this.state.votes}</div>
          <ArrowButton onClick={this.downVote}><Down down={this.state.down}/></ArrowButton>
        </RatingBox>
      </SongBox>
    )
  }
}
  
  export default Song; 