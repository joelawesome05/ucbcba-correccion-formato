import React, { Component } from 'react';
import { HashRouter as HashRouter, Route, Switch } from 'react-router-dom';
import UploadFile from './PagesRoutes/UploadFile';
import CalibratePages from './PagesRoutes/CalibratePages';
import GeneralReport from './PagesRoutes/GeneralReport';
import NotFound from './PagesRoutes/NotFound';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faIgloo } from '@fortawesome/free-solid-svg-icons'

library.add(faIgloo)

class Routes extends Component {
    render() {
        return (
            <HashRouter>
                <Switch>
                    <Route path='/' exact={true} component={UploadFile} />
                    <Route path='/calibrarPaginas/:name' exact={true} component={CalibratePages} />
                    <Route path='/verResultados/:name' exact={true} component={GeneralReport} />
                    <Route component={NotFound} />
                </Switch>
            </HashRouter>
        );
    }
}

export default Routes;
