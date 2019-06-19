// @flow

import React from "react";
import { render } from "react-dom";
import Routes from "./Routes";
import 'bootstrap/dist/css/bootstrap.min.css';

const demoNode = document.querySelector("#demo");

if (demoNode) {
  render(<Routes />, demoNode);
}
