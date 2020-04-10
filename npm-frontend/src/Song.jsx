import React, { Component } from 'react';
import styled from 'styled-components';
import DefaultArt from './defaultart.png';
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
const AlbumTitle = styled.div``;

const Up = styled(ArrowUp)`
  height: 35px;
  color: white;
`;
const Down = styled(ArrowDown)`
  height: 35px;
  color: white;
`;

const ArrowButton = styled.button`
  cursor: pointer;
  background-color: #272726;
  border: #272726;
`;

const RatingBox = styled.div`
  display: flex;
  align-items: center;
`;

class Song extends Component {
  render() {
    return (
      <SongBox>
        <AlbumArt src={this.props.album} />
        <SongInfo>
          <SongTitle>{this.props.title}</SongTitle>
          <ArtistName>{this.props.artist}</ArtistName>
        </SongInfo>
        <RatingBox>
          <ArrowButton><Up /></ArrowButton>
          <div>{this.props.voteCount}</div>
          <ArrowButton><Down /></ArrowButton>
        </RatingBox>
      </SongBox>
    )
  }
}
  
  export default Song; 