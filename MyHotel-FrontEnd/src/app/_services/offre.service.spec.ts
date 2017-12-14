import { TestBed, inject } from '@angular/core/testing';

import { OffreService } from './offre.service';

describe('OffreService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OffreService]
    });
  });

  it('should be created', inject([OffreService], (service: OffreService) => {
    expect(service).toBeTruthy();
  }));
});
