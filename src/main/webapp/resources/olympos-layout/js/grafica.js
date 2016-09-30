 // Blue : #3E98D3  |  Red : #EF3F61  |  Green : #2BB673  |  Orange : #F15732

            function skinChart() {
                this.cfg.shadow = false;
                this.cfg.title = '';
                this.cfg.seriesColors = ['#3E98D3', '#EF3F61', '#2BB673', '#F15732'];
                this.cfg.grid = {
                    background: '#ffffff',
                    borderColor: '#ffffff',
                    gridLineColor: '#F5F5F5',
                    shadow: false
                };
                this.cfg.axesDefaults = {
                    rendererOptions: {
                        textColor: '#666F77'
                    }
                };
                this.cfg.seriesDefaults = {
                    shadow: false,
                    lineWidth: 1,
                    markerOptions: {
                        shadow: false,
                        size: 7,
                        style: 'circle'
                    }
                }
            }