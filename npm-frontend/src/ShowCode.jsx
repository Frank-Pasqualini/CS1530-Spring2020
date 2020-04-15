import React, { Component } from 'react';
import {
  Link
} from "react-router-dom";
import styled from 'styled-components';
import {RightArrowCircle} from 'styled-icons/boxicons-regular/RightArrowCircle';

const EventCode = styled.div`
  color: white;
  font-size: 80px;
  display: flex;
  justify-content: center;
`;
const Loading = styled.div`
  color: white;
  font-size: 50px;
  display: flex;
  justify-content: center;
`;

const Container = styled.div`
  color: white;
  font-size: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 160px 0;
`;

const JoinEventCodeButton = styled.button`
  background-color: #2A77C9;
  color: white;
  height: 131px;
  width: 400px;
  font-size: 55px;
  border-radius: 13px;
  cursor: pointer;
  margin: 35px;
  border-color: #272727;
  font-weight: bold;
  box-shadow: inset 0px 0px 30px #000000, 13px 12px 20px -5px black;
  display: flex;
  justify-content: center;

  transition: width .5s, height .5s, font-size .5s, background-color .5s;

  &:hover {
    height: 150px;
    width: 450px;
    font-size: 60px;
    background-color: #2A77D6;
  }
`;

const Arrow = styled(RightArrowCircle)`
  height: 66px;
  margin: 0px 0px 0px 10px;
  transition: height .5s;

  ${JoinEventCodeButton}:hover & {
    height: 72px;
  }
`;

class ShowCode extends Component {
  constructor() {
    super();
    this.state = {
      loading: true
    }
  }
  componentDidMount() {
    // Change the playlist ID to whatever playlist id you want (it's the playlist URI)
    fetch(`http://localhost:8080/api/add_event?playlistId=37i9dQZF1DX8C9xQcOrE6T&accessToken=${this.props.accessToken}&hostId=${this.props.hostId}`)
    .then(response => response.json())
    .then(data => {
      this.props.addEvent(data);
      this.setState({ loading: false });
    });

  }

  render() {
    return (
      <Container>
        {this.state.loading ? <Loading>Your Event Code is Loading...</Loading> :
        <div>
          This is your Event Code:
          <EventCode>{this.props.currEventCode}</EventCode>
          <Link to='/host' style={{ textDecoration: 'none', display: 'flex', justifyContent: 'center'}}>
            <JoinEventCodeButton>Start Event <Arrow /></JoinEventCodeButton>
          </Link>
        </div>
        }
      </Container>
    );
  }
}

export default ShowCode;
