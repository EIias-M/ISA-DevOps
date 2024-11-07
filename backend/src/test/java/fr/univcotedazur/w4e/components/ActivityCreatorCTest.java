package fr.univcotedazur.w4e.components;


import fr.univcotedazur.w4e.entities.Activity;
import fr.univcotedazur.w4e.repositories.ActivityRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Commit;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;


@Transactional
@Commit

@ExtendWith(MockitoExtension.class)
public class ActivityCreatorCTest {
    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityCreatorC activityCreatorC;

    @Test
    public void saveActivityWhenCreated() {
        Activity activity = new Activity();

        activity.setId(2);
        activity.setnameActivity("Tennis");
        activity.setPrice(10);
        activity.setNbSlot(30);

        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity activityCreated = activityCreatorC.createActivityPackage(activity);

        assertThat(activityCreated.getnameActivity()).isEqualTo("Tennis");
        assertThat(activityCreated.getPrice()).isEqualTo(10);
        assertThat(activityCreated.getNbSlot()).isEqualTo(30);
        assertThat(activityCreated.getId()).isEqualTo(2);
        verify(activityRepository).save(any(Activity.class));
    }

}
