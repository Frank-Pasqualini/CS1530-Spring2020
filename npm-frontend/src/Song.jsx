import React, { Component } from 'react';
import styled from 'styled-components';
import {ArrowUp} from '@styled-icons/entypo/ArrowUp';
import {ArrowDown} from '@styled-icons/entypo/ArrowDown';
import {CancelCircle} from '@styled-icons/icomoon/CancelCircle';


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
  color: #3e97d8;
`;
const Down = styled(ArrowDown)`
  height: 35px;
  color: #3e97d8;
`;

const CancelShape = styled(CancelCircle)`
  height 35px;
  color: #e21717;
  margin: 5px;
`;

const CancelButton = styled.button`
  cursor: pointer;
  background-color: #272726;
  border: #272726;
  outline: none;
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

const SongContainer = styled.div`
  display: flex;
  align-items: center;
`;

const VoteContainer = styled.div`
  margin: 25px;
`;

class Song extends Component {
  constructor(props) {
    super();

    this.state = {
      up: false,
      down: false,
    }
  }

  upVote = () => {
    // Update vote count if up wasn't clicked
    // If up was already clicked, don't do anything
    if(!this.state.up) { 
      this.setState({ up: true, down: false});

      fetch(`http://localhost:8080/api/upvote?eventCode=${this.props.currEventCode}&userId=${this.props.userID}&trackId=${this.props.id}`);

      fetch(`http://localhost:8080/api/event?eventCode=${this.props.currEventCode}`)
      .then(response => response.json())
      .then(data => {
        console.log(data);
        this.props.updateSongs(data);
      });
    }
  }

  downVote = () => {
    // Update vote count if down wasn't clicked
    // If down was already clicked, don't do anything
    if(!this.state.down) { 
      this.setState({ up: false, down: true });

      fetch(`http://localhost:8080/api/downvote?eventCode=${this.props.currEventCode}&userId=${this.props.userID}&trackId=${this.props.id}`);

      fetch(`http://localhost:8080/api/event?eventCode=${this.props.currEventCode}`)
      .then(response => response.json())
      .then(data => {
        this.props.updateSongs(data);
      });
    }
  }

  render() {
    return (
      <SongContainer>
        <SongBox>
          <AlbumArt src={this.props.album} />
          <SongInfo>
            <SongTitle>{this.props.title}</SongTitle>
            <ArtistName>{this.props.artist}</ArtistName>
          </SongInfo>
          {
            this.props.host ? <VoteContainer>{this.props.voteCount}</VoteContainer> :
            <RatingBox>
              <ArrowButton onClick={this.upVote}><Up up={this.state.up}/></ArrowButton>
              <div>{this.props.voteCount}</div>
              <ArrowButton onClick={this.downVote}><Down down={this.state.down}/></ArrowButton>
           </RatingBox>
          } 
          {this.props.removable ? <CancelButton onClick={() => this.props.removeSong(this.props.id)}><CancelShape /></CancelButton> : ''} 
        </SongBox>
      </SongContainer>
    )
  }
}
  
  export default Song; 